package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.{MonixIO, MonixIOScheduler}
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class MonixIOTest {

  @Test
  @DisplayName("testMonixIO")
  def testMonixIO() = {
    MonixIO.run()
  }

  @Test
  @DisplayName("testMonixIOScheduler")
  def testMonixIOScheduler() = {
    val msg = "hi"

    val scheduler = new MonixIOScheduler
    val result = scheduler.run(msg)

    assertEquals(msg, result)
  }

}
