package org.excavator.boot.authorization.autoconfigure

import javax.annotation.Resource
import org.excavator.boot.authorization.autoconfigure.mananger.impl.interceptor.AuthorizationInterceptor
import org.excavator.boot.authorization.config.AuthorizationConfig
import org.excavator.boot.authorization.factory.HandlerMethodArgumentResolverFactory
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.mananger.impl.CacheTokenManager
import org.excavator.boot.authorization.resolver.AuthorizationResolverFactory
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.data.redis.core.StringRedisTemplate


@Configuration
@AutoConfigureAfter(Array(classOf[RedisAutoConfiguration]))
@Import(Array(classOf[AuthorizationInterceptor], classOf[AuthorizationConfig], classOf[HandlerMethodArgumentResolverFactory]))
class AutorizationAutoConfigure {
  val logger = LoggerFactory.getLogger(classOf[AutorizationAutoConfigure])

  @Resource
  val stringRedisTemplate: StringRedisTemplate = null

  @Bean
  def tokenManager: TokenManager = {
    val tokenManager = new CacheTokenManager(stringRedisTemplate)
    logger.info("tokenManager init")
    tokenManager
  }

  @Bean
  def resolversFactoryBean: ServiceLocatorFactoryBean = {
    val serviceLocatorFactoryBean = new ServiceLocatorFactoryBean
    serviceLocatorFactoryBean.setServiceLocatorInterface(classOf[AuthorizationResolverFactory])
    serviceLocatorFactoryBean
  }
}