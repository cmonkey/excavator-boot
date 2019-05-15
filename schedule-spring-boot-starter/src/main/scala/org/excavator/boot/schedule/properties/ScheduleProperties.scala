package org.excavator.boot.schedule.properties

import org.springframework.boot.context.properties.ConfigurationProperties

import scala.beans.BeanProperty

@ConfigurationProperties(value = "excavator.schedule")
case class ScheduleProperties() {
  @BeanProperty
  var threadPool: ThreadPoolProperties = null
  @BeanProperty
  var threadNamePrefix: String = "excavator-task-"
}
