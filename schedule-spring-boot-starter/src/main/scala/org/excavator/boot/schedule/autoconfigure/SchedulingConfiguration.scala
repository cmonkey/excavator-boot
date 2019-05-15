package org.excavator.boot.schedule.autoconfigure

import java.util.Optional
import java.util.concurrent.{RejectedExecutionException, RejectedExecutionHandler, ThreadPoolExecutor}

import javax.annotation.Resource
import org.excavator.boot.schedule.exception.StopSchedulingTaskException
import org.excavator.boot.schedule.properties.ScheduleProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.scheduling.annotation.{EnableScheduling, SchedulingConfigurer}
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
@EnableScheduling
class SchedulingConfiguration extends SchedulingConfigurer{

  val DEFAULT_POOLSIZE = 10
  val DEFAULT_AWAIT_TERMINATION_SECONDS = 60
  val DEFAULT_WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN = true
  val logger = LoggerFactory.getLogger(classOf[SchedulingConfiguration])

  @Configuration
  @EnableConfigurationProperties(Array(classOf[ScheduleProperties]))
  class ScheduleThreadPool(scheduleProperties: ScheduleProperties){

    def getDefaultIfNull(poolSize: Int, defaultPoolSize: Int): Int = {
      if(null == poolSize){
        defaultPoolSize
      }else{
        poolSize
      }
    }

    @Bean
    def taskScheduler() = {
      val scheduler = new ThreadPoolTaskScheduler
      scheduler.setPoolSize(getDefaultIfNull(scheduleProperties.getThreadPool.getPoolSize, DEFAULT_POOLSIZE))
      scheduler.setErrorHandler((t: Throwable) => {
        logger.error(s"Unexpected error occured in scheduled task. ${t}")

        if(t.isInstanceOf[StopSchedulingTaskException]){
          logger.error("Stop scheduling task becauss of occuring StopSchedulingTaskException in task")
          throw t.asInstanceOf[StopSchedulingTaskException]
        }
      })

      scheduler.setThreadNamePrefix(scheduleProperties.getThreadNamePrefix)

      scheduler.setAwaitTerminationSeconds(getDefaultIfNull(scheduleProperties.getThreadPool.getAwaitTerminaltionSeconds,
        DEFAULT_AWAIT_TERMINATION_SECONDS))
      scheduler.setWaitForTasksToCompleteOnShutdown(Optional.ofNullable(scheduleProperties.getThreadPool.getWaitForTasksToCompleteOnShutdown)
        .orElse(DEFAULT_WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN))

      scheduler.setRejectedExecutionHandler(new RejectedExecutionHandler {
        override def rejectedExecution(r: Runnable, executor: ThreadPoolExecutor): Unit = {
          logger.error("Please increase pool size excavator.schedule.threadPool.poolSize for thread pool")
          throw new RejectedExecutionException(s"Task ${r.toString} rejected from ${executor.toString}")
        }
      })

      scheduler
    }
  }

  @Resource(name = "taskScheduler")
  val taskScheduler: ThreadPoolTaskScheduler = null

  override def configureTasks(taskRegistrar: ScheduledTaskRegistrar): Unit = {
    taskRegistrar.setScheduler(taskScheduler)
  }
}
