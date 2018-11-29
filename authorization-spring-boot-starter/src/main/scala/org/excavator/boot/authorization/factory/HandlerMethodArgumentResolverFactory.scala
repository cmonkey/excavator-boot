package org.excavator.boot.authorization.factory

import java.util
import java.util.{Arrays, List}
import java.util.stream.Collectors

import com.google.common.collect.Lists
import javax.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.excavator.boot.authorization.resolver.AuthorizationResolverFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodArgumentResolver

@Component
class HandlerMethodArgumentResolverFactory {
  @Value("${authorization.method.resolvers:empty}")
  private val resolvers: String = null
  @Resource
  private[autoconfigure] val authorizationResolverFactory: AuthorizationResolverFactory = null

  def getServices: util.List[HandlerMethodArgumentResolver] =
    if (StringUtils.isNotEmpty(resolvers) && !("empty" == resolvers))
      util.Arrays.stream(resolvers.split(",")).map((instance: String) => authorizationResolverFactory.getService(instance)).collect(Collectors.toList)
  else Lists.newArrayList
}