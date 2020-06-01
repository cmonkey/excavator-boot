package org.excavator.boot.authorization.factory

import java.util

import com.google.common.collect.Lists
import javax.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.excavator.boot.authorization.config.AuthorizationProperties
import org.excavator.boot.authorization.resolver.AuthorizationResolverFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodArgumentResolver

import scala.jdk.CollectionConverters._

@Component
class HandlerMethodArgumentResolverFactory {
  @Resource
  val authorizationProperties: AuthorizationProperties = null

  @Resource
  val authorizationResolverFactory: AuthorizationResolverFactory = null

  def getServices: util.List[HandlerMethodArgumentResolver] =
    if (StringUtils.isNotEmpty(authorizationProperties.getResolvers)){
      authorizationProperties.getResolvers.split(",").map(instance => {
        authorizationResolverFactory.getService(instance)
      }).toList.asJava
    }else{
      val empty:util.List[HandlerMethodArgumentResolver] = Lists.newArrayList[HandlerMethodArgumentResolver]

      empty
    }
}