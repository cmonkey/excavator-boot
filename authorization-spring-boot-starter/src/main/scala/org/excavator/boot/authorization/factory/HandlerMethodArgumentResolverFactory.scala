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

import scala.collection.JavaConverters._

@Component
class HandlerMethodArgumentResolverFactory {
  @Value("${authorization.method.resolvers:empty}")
  private val resolvers: String = null
  @Resource
  val authorizationResolverFactory: AuthorizationResolverFactory = null

  def getServices: util.List[HandlerMethodArgumentResolver] =
    if (StringUtils.isNotEmpty(resolvers) && !("empty" == resolvers)){
      resolvers.split(",").map(instance => {
        authorizationResolverFactory.getService(instance)
      }).toList.asJava
    }else{
      val empty:List[HandlerMethodArgumentResolver] = Lists.newArrayList[HandlerMethodArgumentResolver]

      empty
    }
}