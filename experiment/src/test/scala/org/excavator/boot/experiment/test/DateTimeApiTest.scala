package org.excavator.boot.experiment.test

import org.junit.jupiter.api.{DisplayName, Test}

import java.time.{LocalDate, Month, LocalTime}

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

  @Test
  @DisplayName("test LocalTime")
  def testLocalTime():Unit = {
    val localTimeNow = LocalTime.now()
    val localTimeOf = LocalTime.of(15, 12, 16)
    val localTimeOf2 = localTimeOf.plusMinutes(33).minusHours(2)

    val localTimeParsed = LocalTime.parse("21:55")

    println(s"localTimeNow = ${localTimeNow}")
    println(s"localTimeNow = ${localTimeOf}")
    println(s"localTimeNow = ${localTimeOf2}")
    println(s"localTimeNow = ${localTimeParsed}")
  }

}
