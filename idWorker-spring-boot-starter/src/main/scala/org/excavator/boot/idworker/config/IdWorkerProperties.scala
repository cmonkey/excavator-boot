package org.excavator.boot.idworker.config

import org.springframework.boot.context.properties.ConfigurationProperties

import scala.beans.BeanProperty

@ConfigurationProperties("excavator.idworker")
case class IdWorkerProperties() {
  @BeanProperty var group: String = "default"

  @BeanProperty var serverList: String = _

  @BeanProperty var namespace: String = "idWorker"

  @BeanProperty var baseSleepTimeMillisSeconds: Int = 1000

  @BeanProperty var maxSleepTimeMillisSeconds: Int = 3000

  @BeanProperty var maxRetries: Int = 3

  @BeanProperty var sessionTimeoutMillisSeconds: Int = _

  @BeanProperty var connectionTimeoutMillisSeconds: Int = _

  @BeanProperty var digest: String = _
}
