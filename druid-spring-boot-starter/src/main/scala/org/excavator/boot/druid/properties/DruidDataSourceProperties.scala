package org.excavator.boot.druid.properties

import org.springframework.boot.context.properties.ConfigurationProperties

import scala.beans.BeanProperty

@ConfigurationProperties(prefix = "spring.datasource")
case class DruidDataSourceProperties() {
  @BeanProperty
  var url:String = _

  @BeanProperty
  var driverClassName:String = _

  @BeanProperty
  var `type`:String = _

  @BeanProperty
  var username:String = _

  @BeanProperty
  var password:String = _

  @BeanProperty
  var initialSize:Int = 0

  @BeanProperty
  var minIdle:Int = 0

  @BeanProperty
  var maxActive:Int = 0

  @BeanProperty
  var maxWait:Long = 0L

  @BeanProperty
  var timeBetweenEvictionRunsMillis:Long = 0L

  @BeanProperty
  var minEvictableIdleTimeMillis:Long = 0L

  @BeanProperty
  var validationQuery:String = _

  @BeanProperty
  var testWhileIdle:Boolean = false

  @BeanProperty
  var testOnBorrow:Boolean = false

  @BeanProperty
  var testOnReturn:Boolean = false

  @BeanProperty
  var poolPreparedStatements:Boolean = false

  @BeanProperty
  var maxPoolPreparedStatementPerConnectionSize:Int = 0

  @BeanProperty
  var filters:String = _

  @BeanProperty
  var connectionProperties:String = _
}
