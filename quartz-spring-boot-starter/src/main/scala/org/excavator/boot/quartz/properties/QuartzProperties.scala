package org.excavator.boot.quartz.properties

import org.springframework.boot.context.properties.ConfigurationProperties

import scala.beans.BeanProperty

@ConfigurationProperties("excavator.quartz")
case class QuartzProperties (){
  @BeanProperty
  var driverClassName:String = null

  @BeanProperty
  var url:String = null

  @BeanProperty
  var username:String = null

  @BeanProperty
  var password:String = null

  @BeanProperty
  var initialSize:Int = 0

  @BeanProperty
  var minIdle: Int = 0

  @BeanProperty
  var maxActive:Int = 0

  @BeanProperty
  var maxWait: Long = 0

  @BeanProperty
  var timeBetweenEvictionRunsMillis: Long = 0

  @BeanProperty
  var minEvictableIdleTimeMillis: Long = 0

  @BeanProperty
  var validationQuery: String = null

  @BeanProperty
  var testWhileIdle: Boolean = false

  @BeanProperty
  var testOnBorrow: Boolean = false

  @BeanProperty
  var testOnReturn: Boolean = false

  @BeanProperty
  var poolPreparedStatements: Boolean = false

  @BeanProperty
  var maxPoolPreparedStatementPerConnectionSize: Int = 0

  @BeanProperty
  var filters: String = null

  @BeanProperty
  var connectionProperties: String = null
}
