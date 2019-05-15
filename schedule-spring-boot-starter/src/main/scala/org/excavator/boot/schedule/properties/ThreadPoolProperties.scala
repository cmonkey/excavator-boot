package org.excavator.boot.schedule.properties

import scala.beans.BeanProperty

case class ThreadPoolProperties() {
  @BeanProperty
  var poolSize: Int = _

  @BeanProperty
  var awaitTerminaltionSeconds: Int = _

  @BeanProperty
  var waitForTasksToCompleteOnShutdonw: Boolean = _

}
