package org.excavator.boot.authorization.config

import org.springframework.boot.context.properties.ConfigurationProperties

import scala.beans.BeanProperty

@ConfigurationProperties("excavator.authorization")
case class AuthorizationProperties() {
  @BeanProperty var resolvers: String = ""

  @BeanProperty var expire_second: Long = 3
}
