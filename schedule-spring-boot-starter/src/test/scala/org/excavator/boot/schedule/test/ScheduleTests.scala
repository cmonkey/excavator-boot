package org.excavator.boot.schedule.test

import javax.annotation.Resource
import org.excavator.boot.schedule.properties.ScheduleProperties
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions._
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[ScheduleApplication]))
class ScheduleTests {

  val logger = LoggerFactory.getLogger(classOf[ScheduleTests])

  @Resource
  val taskService: TaskService = null

  @Resource
  val scheduleProperties: ScheduleProperties = null

  @Test
  def testTask() = {

    assertTrue(taskService.getThreadName().startsWith(scheduleProperties.getThreadNamePrefix))
  }
}
