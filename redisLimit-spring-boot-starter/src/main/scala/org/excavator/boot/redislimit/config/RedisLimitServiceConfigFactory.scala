package org.excavator.boot.redislimit.config

import javax.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.excavator.boot.redislimit.factory.RedisLimitServiceFactory
import org.excavator.boot.redislimit.plugin.DefaultRedisLimitService
import org.excavator.boot.redislimit.service.RedisLimitService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RedisLimitServiceConfigFactory {
  val logger = LoggerFactory.getLogger(classOf[RedisLimitServiceConfigFactory])

  @Value("${redis.limit.component:defaultRedisLimitService}")
  val limitComponent: String = null

  @Resource
  val redisLimitServiceFactory: RedisLimitServiceFactory = null

  @Bean(name = Array("defaultRedisLimitService"))
  @ConditionalOnMissingBean(name = Array("defaultRedisLimitService"))
  def defaultRedisLimitService(): RedisLimitService = {
    new DefaultRedisLimitService()
  }

  @Bean
  def redisLimitService(): RedisLimitService = {
    if(StringUtils.isBlank(limitComponent) || redisLimitServiceFactory == null){
      logger.error("redis limit component is null")
      null
    }else{
      val redisLimitService = redisLimitServiceFactory.getService(limitComponent)

      redisLimitService
    }
  }
}
