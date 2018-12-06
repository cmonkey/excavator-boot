package org.excavator.boot.quartz.test

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Qualifier, Value}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.scheduling.quartz.{CronTriggerFactoryBean, JobDetailFactoryBean}

@Configuration
class QuartzJobConfig {
  val logger = LoggerFactory.getLogger(classOf[QuartzJobConfig])

  @Value("${spring.application.name:quartz-job}")
  var applicationName:String = ""

  @Bean(name = Array("customBatchJobBean"))
  def customBatchJobBean(): JobDetailFactoryBean = {

    val jobDetail = new JobDetailFactoryBean

    jobDetail.setName("customJob")
    jobDetail.setGroup(applicationName)
    jobDetail.setJobClass(classOf[QuartzBatchJobBean])

    jobDetail.setDurability(true)

    logger.info(s"customBatchJobBean init = ${jobDetail}")

    jobDetail
  }

  @Bean(name = Array("customJobTrigger"))
  def customJobTrigger(@Qualifier("customBatchJobBean")
                       jobDetailFactoryBean: JobDetailFactoryBean):CronTriggerFactoryBean = {

    val trigger = new CronTriggerFactoryBean

    trigger.setJobDetail(jobDetailFactoryBean.getObject)
    trigger.setCronExpression("0/1 * * * * ?")
    trigger.setName("customJobTrigger")

    logger.info(s"customJobTrigger init = ${trigger}")

    trigger
  }
}
