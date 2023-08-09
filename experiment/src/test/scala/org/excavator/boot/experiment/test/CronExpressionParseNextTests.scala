package org.excavator.boot.experiment.test

import org.junit.jupiter.api.{DisplayName, Test}
import org.springframework.scheduling.support.CronExpression

import java.time.LocalDateTime

class CronExpressionParseNextTests {

  @Test
  @DisplayName("test cron expression parse next")
  def testCronExpressionParseNext():Unit = {

    val expression = "0 0 0 * * *"
    val nextTime = CronExpression.parse(expression).next(LocalDateTime.now())
    println(s"nextTime is ${nextTime}")
  }

}
