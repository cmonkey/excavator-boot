package org.excavator.boot.authorization.config

import java.util
import java.util.List

import javax.annotation.Resource
import org.excavator.boot.authorization.autoconfigure.mananger.impl.interceptor.AuthorizationInterceptor
import org.excavator.boot.authorization.factory.HandlerMethodArgumentResolverFactory
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.context.annotation.Configuration
import org.springframework.util.CollectionUtils
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.{InterceptorRegistry, WebMvcConfigurerAdapter}

@Configuration
class AuthorizationConfig extends WebMvcConfigurerAdapter {
  val logger = LoggerFactory.getLogger(classOf[AuthorizationConfig])

  @Resource
  private val authorizationInterceptor: AuthorizationInterceptor = null
  @Resource
  private val handlerMethodArgumentResolverFactory: HandlerMethodArgumentResolverFactory = null

  override def addInterceptors(registry: InterceptorRegistry): Unit = {
    registry.addInterceptor(authorizationInterceptor)
  }

  override def addArgumentResolvers(argumentResolvers: util.List[HandlerMethodArgumentResolver]): Unit = {
    val services: util.List[HandlerMethodArgumentResolver] = handlerMethodArgumentResolverFactory.getServices

    logger.info("addArgumentResolvers resolvers custom services = {}", services)

    if (!CollectionUtils.isEmpty(services)) argumentResolvers.addAll(services)
  }
}