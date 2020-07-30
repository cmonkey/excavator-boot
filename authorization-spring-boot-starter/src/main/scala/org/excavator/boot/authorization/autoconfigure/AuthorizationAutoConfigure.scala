package org.excavator.boot.authorization.autoconfigure

import org.excavator.boot.authorization.interceptor.{AuthorizationInterceptor, AuthorizationResolver}
import org.excavator.boot.authorization.config.{AuthorizationConfig, AuthorizationProperties}
import org.excavator.boot.authorization.factory.HandlerMethodArgumentResolverFactory
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.mananger.impl.CacheTokenManager
import org.excavator.boot.authorization.resolver.AuthorizationResolverFactory
import org.excavator.boot.helper.CustomerHelper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.data.redis.core.StringRedisTemplate


@Configuration
@AutoConfigureAfter(Array(classOf[RedisAutoConfiguration]))
@EnableConfigurationProperties(Array(classOf[AuthorizationProperties]))
@Import(Array(classOf[AuthorizationInterceptor], classOf[AuthorizationConfig], classOf[HandlerMethodArgumentResolverFactory], classOf[CustomerHelper]))
class AuthorizationAutoConfigure {
  private val logger = LoggerFactory.getLogger(classOf[AuthorizationAutoConfigure])

  @Bean
  def tokenManager(@Qualifier("stringRedisTemplate") stringRedisTemplate: StringRedisTemplate): TokenManager = {
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

  @ConditionalOnMissingBean
  @Bean
  def authorizationResolver(): AuthorizationResolver = {

    val authorizationResolver = new AuthorizationResolver

    logger.info("authorizationResolver init ")

    authorizationResolver
  }
}