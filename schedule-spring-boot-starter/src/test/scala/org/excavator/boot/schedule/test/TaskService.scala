package org.excavator.boot.schedule.test

import java.util.concurrent.atomic.AtomicReference

import org.excavator.boot.schedule.exception.StopSchedulingTaskException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TaskService {

  val logger = LoggerFactory.getLogger(classOf[TaskService])

  val atomic = new AtomicReference[String]()

  @Scheduled(fixedRate =  1 * 60)
  def task() = {
    atomic.set(Thread.currentThread().getName)
    logger.info(s"Thread currentThread Name = ${atomic.get()}")
  }

  def getThreadName() = {
    atomic.get()
  }

  @Scheduled(fixedRate =  1 * 60)
  def taskErr() = {
    logger.info("task error")
    throw new StopSchedulingTaskException()
  }

}
