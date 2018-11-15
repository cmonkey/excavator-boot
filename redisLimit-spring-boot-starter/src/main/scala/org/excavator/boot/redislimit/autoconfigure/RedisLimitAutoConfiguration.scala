package org.excavator.boot.redislimit.autoconfigure

import com.fasterxml.jackson.annotation.{JsonAutoDetect, PropertyAccessor}
import com.fasterxml.jackson.databind.ObjectMapper
import org.excavator.boot.redislimit.config.RedisLimitServiceConfigFactory
import org.excavator.boot.redislimit.factory.RedisLimitServiceFactory
import org.excavator.boot.redislimit.handler.{RedisLimitAspectHandler, RedisLimitExecute}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.data.redis.serializer.{Jackson2JsonRedisSerializer, StringRedisSerializer}
import org.springframework.scripting.support.ResourceScriptSource

@Configuration
@EnableCaching
@Import(Array(classOf[RedisLimitServiceConfigFactory],
  classOf[RedisLimitExecute],
  classOf[RedisLimitAspectHandler]))
class RedisLimitAutoConfiguration{

  val logger = LoggerFactory.getLogger(classOf[RedisLimitAutoConfiguration])

  @Bean
  @ConditionalOnMissingBean
  def cacheManager(redisTemplate: RedisTemplate[Any, Any]) = {
    val cacheManager = new RedisCacheManager(redisTemplate)

    logger.info("cacheManager in redisCacheManager init ")

    cacheManager
  }

  @Bean(name = Array("redisLimitTemplate"))
  @ConditionalOnMissingBean(name = Array("redisLimitTemplate"))
  def redisLimitTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate[String, Object] = {
    val redisTemplate = new RedisTemplate[String, Object]()

    redisTemplate.setConnectionFactory(redisConnectionFactory)

    val serializer = new Jackson2JsonRedisSerializer(classOf[Object])

    val mapper = new ObjectMapper()

    mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
    mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)

    serializer.setObjectMapper(mapper)

    redisTemplate.setValueSerializer(serializer)
    redisTemplate.setKeySerializer(new StringRedisSerializer())
    redisTemplate.afterPropertiesSet()

    logger.info("redisTemplate init in redisLimiter")

    redisTemplate
  }

  @Bean(name = Array("redisLimitScript"))
  @ConditionalOnMissingBean(name = Array("redisLimitScript"))
  def defaultRedisScript(): DefaultRedisScript[Long] = {
    val defaultRedisScript = new DefaultRedisScript[Long]()

    defaultRedisScript.setResultType(classOf[Long])
    defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redisLimiter.lua")))

    logger.info("defaultRedisScript init")

    defaultRedisScript

  }

  @Bean
  def redisLimitServiceLocatorFactoryBean(): ServiceLocatorFactoryBean = {
    val serviceLocatorFactoryBean = new ServiceLocatorFactoryBean() 

    serviceLocatorFactoryBean.setServiceLocatorInterface(classOf[RedisLimitServiceFactory])

    logger.info(s"serviceLocatorFactoryBean redisLimitServiceFactory init = ${serviceLocatorFactoryBean}")

    serviceLocatorFactoryBean
  }

}
