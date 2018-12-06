package org.excavator.boot.quartz.test

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

import javax.annotation.Resource
import org.junit.Test
import org.junit.Assert._
import org.junit.runner.RunWith
import org.quartz.{JobExecutionContext, JobExecutionException, JobListener, Scheduler}
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[QuartzApplication]))
class QuartzTests {
  val logger = LoggerFactory.getLogger(classOf[QuartzTests])

  @Resource
  val scheduler: Scheduler = null

  @Test
  def contentTests() = {
    val result = new AtomicReference[String]()
    scheduler.getListenerManager.addJobListener(new JobListener {
      override def getName: String = {
        "listener"
      }

      override def jobToBeExecuted(jobExecutionContext: JobExecutionContext): Unit = {
        logger.info(s"executed = ${jobExecutionContext.getResult}")
      }

      override def jobExecutionVetoed(jobExecutionContext: JobExecutionContext): Unit = {
        logger.info(s"executionVetoed = ${jobExecutionContext.getResult}")
      }

      override def jobWasExecuted(jobExecutionContext: JobExecutionContext, e: JobExecutionException): Unit = {
        result.set(jobExecutionContext.getResult.toString)
        logger.info(s"wasExecuted = ${jobExecutionContext.getResult}")

      }
    })

    TimeUnit.SECONDS.sleep(15)

    assertEquals(QuartzConstants.msg, result.get())

  }
}
