package org.excavator.boot.idworker.autoconfigure

import java.nio.charset.StandardCharsets
import java.util
import java.util.concurrent.TimeUnit

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.framework.api.ACLProvider
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.zookeeper.data.ACL
import org.apache.zookeeper.{KeeperException, ZooDefs}
import org.excavator.boot.idworker.config.IdWorkerProperties
import org.excavator.boot.idworker.storage.SnowflakeNodeRegister
import org.excavator.boot.idworker.generator.{IdGenerator, SnowflakeGenerator}
import org.excavator.boot.idworker.registry.ZookeeperRegistryCenter
import org.excavator.boot.idworker.storage.SnowflakeNodePath
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.{ConditionalOnClass, ConditionalOnMissingBean}
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
@ConditionalOnClass(Array(classOf[IdGenerator]))
@EnableConfigurationProperties(Array(classOf[IdWorkerProperties]))
class IdWorkerAutoConfigure {

  private val logger = LoggerFactory.getLogger(classOf[IdWorkerAutoConfigure])

  @Autowired
  val idWorkerProperties: IdWorkerProperties = null

  @Bean
  @ConditionalOnMissingBean
  def idGenerator(curatorFramework: CuratorFramework): IdGenerator = {
    val registerCenter = new ZookeeperRegistryCenter(idWorkerProperties, curatorFramework)

    registerCenter.init()

    val snowflakeNodePath = new SnowflakeNodePath(idWorkerProperties.namespace, idWorkerProperties.group)

    val snowflakeNodeRegister = new SnowflakeNodeRegister(registerCenter, snowflakeNodePath)

    val generator = new SnowflakeGenerator(snowflakeNodeRegister)

    generator.init()

    generator
  }

  @Bean(destroyMethod = "close")
  @ConditionalOnMissingBean
  def curatorFramework(): CuratorFramework = {
    val exponentialBackoffRetry = new ExponentialBackoffRetry(idWorkerProperties.baseSleepTimeMillisSeconds,
      idWorkerProperties.maxRetries,
      idWorkerProperties.maxSleepTimeMillisSeconds)

    val builder = CuratorFrameworkFactory.builder().connectString(idWorkerProperties.serverList)
      .retryPolicy(exponentialBackoffRetry)

    if(0 != idWorkerProperties.sessionTimeoutMillisSeconds){
      builder.sessionTimeoutMs(idWorkerProperties.sessionTimeoutMillisSeconds)
    }

    if(0 != idWorkerProperties.connectionTimeoutMillisSeconds){
      builder.connectionTimeoutMs(idWorkerProperties.connectionTimeoutMillisSeconds)
    }

    if(idWorkerProperties.digest != null &&
      !idWorkerProperties.digest.isEmpty){
      builder.authorization("digest", idWorkerProperties.digest.getBytes(StandardCharsets.UTF_8)).aclProvider(new ACLProvider {
        override def getDefaultAcl: util.List[ACL] = ZooDefs.Ids.CREATOR_ALL_ACL

        override def getAclForPath(path: String): util.List[ACL] = ZooDefs.Ids.CREATOR_ALL_ACL
      })
    }

    val curatorFramework = builder.build()

    curatorFramework.start()

    try{
      if(!curatorFramework.blockUntilConnected(idWorkerProperties.maxSleepTimeMillisSeconds * idWorkerProperties.maxRetries, TimeUnit.MILLISECONDS)){
        curatorFramework.close()
        throw new KeeperException.OperationTimeoutException()
      }
    }catch {

      case ex: KeeperException.OperationTimeoutException => logger.error("init KeeperException = {}", ex)
      case ex: InterruptedException => logger.error("init InterruptedException = {}", ex)
    }

    curatorFramework
  }

}
