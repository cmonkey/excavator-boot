package org.excavator.boot.quartz.factory

import javax.annotation.Resource
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.scheduling.quartz.AdaptableJobFactory
import org.quartz.spi.TriggerFiredBundle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SpringJobFactory extends AdaptableJobFactory {
  val logger = LoggerFactory.getLogger(classOf[SpringJobFactory])

  @Resource
  val autowireCapableBeanFactory:AutowireCapableBeanFactory = null

  override def createJobInstance(bundle: TriggerFiredBundle): AnyRef = {
    val instance = super.createJobInstance(bundle)

    autowireCapableBeanFactory.autowireBean(instance)

    logger.info(s"createJobInstance = $instance")

    instance
  }
}
