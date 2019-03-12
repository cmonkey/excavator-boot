package org.excavator.boot.quartz.autoconfigure

import java.util.Properties

import org.excavator.boot.quartz.config.QuartzDataSourceConfig
import org.excavator.boot.quartz.factory.SpringJobFactory
import org.excavator.boot.quartz.properties.QuartzProperties
import javax.annotation.Resource
import javax.sql.DataSource
import org.quartz._
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.scheduling.quartz.{CronTriggerFactoryBean, JobDetailFactoryBean, SchedulerFactoryBean}

@Configuration
@EnableConfigurationProperties(Array(classOf[QuartzProperties]))
@Import(Array(classOf[QuartzDataSourceConfig],
  classOf[SpringJobFactory]))
class QuartzAutoConfiguration{

  val logger = LoggerFactory.getLogger(classOf[QuartzAutoConfiguration])

  @Value("${spring.application.name:quartz-job}")
  var applicationName:String = ""

  @Resource
  val quartzDataSource: DataSource = null

  @Resource
  val springJobFactory: SpringJobFactory = null

  @Bean def schedulerFactoryBean(triggers: Array[Trigger]): SchedulerFactoryBean = {

    val schedulerFactoryBean = new SchedulerFactoryBean

    schedulerFactoryBean.setOverwriteExistingJobs(true)
    schedulerFactoryBean.setStartupDelay(10)
    schedulerFactoryBean.setAutoStartup(true)
    schedulerFactoryBean.setDataSource(quartzDataSource)
    schedulerFactoryBean.setJobFactory(springJobFactory)
    schedulerFactoryBean.setSchedulerName(applicationName)
    schedulerFactoryBean.setQuartzProperties(getProperties)
    schedulerFactoryBean.setTriggers(triggers:_*)

    logger.info(s"schedulerFactoryBean init = $schedulerFactoryBean")

    schedulerFactoryBean
  }

  @Bean
  def scheduler(schedulerFactoryBean: SchedulerFactoryBean): Scheduler = schedulerFactoryBean.getScheduler

  def createJobDetail(jobClass: Class[Job], description: String): JobDetailFactoryBean = {
    val factoryBean = new JobDetailFactoryBean

    factoryBean.setJobClass(jobClass)
    factoryBean.setDurability(true)
    factoryBean.setDescription(description)

    logger.info(s"createJobDetail init $factoryBean")

    factoryBean
  }

  def createCronTrigger(jobDetail: JobDetail, frequency: String, description: String): CronTriggerFactoryBean = {
    val factoryBean = new CronTriggerFactoryBean

    factoryBean.setJobDetail(jobDetail)
    factoryBean.setCronExpression(frequency)
    factoryBean.setDescription(description)
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW)

    logger.info(s"createCronTrigger init $factoryBean")

    factoryBean
  }

  private def getProperties = {
    val prop = new Properties

    prop.put("org.quartz.scheduler.instanceName", applicationName)
    prop.put("org.quartz.scheduler.instanceId", "AUTO")
    prop.put("org.quartz.scheduler.skipUpdateCheck", "true")
    prop.put("org.quartz.scheduler.jmx.export", "true")
    prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX")
    prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate")
    prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_")
    prop.put("org.quartz.jobStore.isClustered", "true")
    prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000")
    prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1")
    prop.put("org.quartz.jobStore.misfireThreshold", "60000")
    prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true")
    prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE")
    prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool")
    prop.put("org.quartz.threadPool.threadCount", "50")
    prop.put("org.quartz.threadPool.threadPriority", "5")
    prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true")
    prop.put("org.quartz.plugin.triggHistory.class", "org.quartz.plugins.history.LoggingJobHistoryPlugin")
    prop.put("org.quartz.plugin.shutdownhook.class", "org.quartz.plugins.management.ShutdownHookPlugin")
    prop.put("org.quartz.plugin.shutdownhook.cleanShutdown", "true")

    logger.info(s"getProperties init = $prop")

    prop
  }
}
