package org.excavator.boot.experiment.test

import org.junit.jupiter.api.{DisplayName, Test}

import java.time.{LocalDate, Month}

class DateTimeApiTest {

  @Test
  @DisplayName("test localDate")
  def testLocalDate(): Unit = {
    val localDate = LocalDate.now()
    val localDateOf = LocalDate.of(2019, 2,15)
    val localDateOf2 = LocalDate.of(2019, Month.FEBRUARY, 15)
    println(s"localDate = ${localDate}")
    println(s"localDateOf = ${localDateOf}")
    println(s"localDateOf2 = ${localDateOf2}")
  }

}
