package org.excavator.boot.idworker.registry

import java.nio.charset.StandardCharsets
import java.util.Collections

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.utils.CloseableUtils
import org.apache.zookeeper.{CreateMode}
import org.excavator.boot.idworker.config.IdWorkerProperties
import org.slf4j.LoggerFactory

class ZookeeperRegistryCenter(idWorkerProperties: IdWorkerProperties, curatorFramework: CuratorFramework) extends RegisterCenter {

  val logger = LoggerFactory.getLogger(classOf[ZookeeperRegistryCenter])

  override def init(): Unit = {
    logger.info("ZookeeperRegistryCenter init")
  }

  override def close(): Unit = {
    CloseableUtils.closeQuietly(curatorFramework)
  }

  override def get(key: String): String = {
    ""
  }

  override def isExisted(key: String): Boolean = {
    try{
      null != curatorFramework.checkExists().forPath(key)
    }catch {
      case ex: Exception => logger.error("isExisted Exception = {}", ex)
    }

    false
  }

  override def persist(key: String, value: String): Unit = ???

  override def update(key: String, value: String): Unit = ???

  override def remove(key: String): Unit = {
    try{
      curatorFramework.delete().deletingChildrenIfNeeded().forPath(key)
    }catch{
      case ex: Exception => logger.error("remove Exception = {}", ex)
    }
  }

  override def getChildrenNum(key: String): Int = {
    try{
      val stat = curatorFramework.checkExists().forPath(key)
      if(null != stat){
        stat.getNumChildren
      }else{
        0
      }
    }catch{
      case ex: Exception => 0
    }
  }

  override def getChildrenKeys(key: String): java.util.List[String] = {
    try{
      val result = curatorFramework.getChildren.forPath(key)
      result.sort((o1, o2) => o2.compareTo(o1))
      result
    }catch{
      case ex: Exception => Collections.emptyList()
    }
  }

  override def persistEphemeral(key: String, value: String): Unit = {
    try{
      if(isExisted(key)){
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(key)
      }

      curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(StandardCharsets.UTF_8))
    }catch{
      case ex: Exception => logger.error("persistEphemeral Exception = {}", ex)
    }
  }

  override def getRawClient(): AnyRef = {
    curatorFramework
  }
}
