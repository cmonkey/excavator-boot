package org.excavator.boot.druid.properties

import org.springframework.boot.context.properties.ConfigurationProperties

import scala.beans.BeanProperty

@ConfigurationProperties(prefix = "druid.monitor")
case class DruidMonitorProperties() {
  @BeanProperty
  var statView:String = _;

  @BeanProperty
  var allow:String = _ ;

  @BeanProperty
  var deny:String = _;

  @BeanProperty
  var loginName:String = _;

  @BeanProperty
  var loginPassword:String = _;

  @BeanProperty
  var resetEnable:String = _;

  @BeanProperty
  var urlPatterns:String = _;

  @BeanProperty
  var exclusions:String = _;

}
