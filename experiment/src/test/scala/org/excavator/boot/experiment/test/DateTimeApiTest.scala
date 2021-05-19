package org.excavator.boot.experiment.test

import org.junit.jupiter.api.{DisplayName, Test}

import java.time.temporal.ChronoUnit
import java.time.{Duration, Instant, LocalDate, LocalDateTime, LocalTime, Month, Period, ZoneId, ZonedDateTime}

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

  @Test
  @DisplayName("test LocalDateTime")
  def testLocalDateTime():Unit = {
    val localDate = LocalDate.now()
    val localTime = LocalTime.now()

    val localDateTimeNow = LocalDateTime.of(localDate, localTime)
    val specificLocalDateTime = LocalDateTime.of(2011, Month.JANUARY, 15, 15, 33, 22)
    
    println(s"localDateTimeNow = ${localDateTimeNow}")
    println(s"specificLocalDateTimeNow = ${specificLocalDateTime}")
  }

  @Test
  @DisplayName("test ZoneIds")
  def testZoneIds(): Unit = {
    val zoneId = ZoneId.systemDefault()
    println(s"zoneIds = ${zoneId}")

    val availableZoneIds = ZoneId.getAvailableZoneIds
    println(s"availableZoneIds = ${availableZoneIds}")

    val romeZoneId = ZoneId.of("Europe/Rome")
    val localDateTime = LocalDateTime.now(romeZoneId)

    println(s"localDateTime = ${localDateTime}")
  }

  @Test
  @DisplayName("testZonedDateTime")
  def testZonedDateTime(): Unit = {
    val currentLocationZonedDateTime = ZonedDateTime.now()
    println(s"currentLocationZonedDateTime = ${currentLocationZonedDateTime}")

    val localDateTime = LocalDateTime.now()
    val currentLoationZoneId = ZoneId.systemDefault()
    val zonedDateTime = ZonedDateTime.of(localDateTime,currentLoationZoneId
      )
    
    println(s"zonedDateTime = ${zonedDateTime}")
  }

  @Test
  @DisplayName("test Instant")
  def testInstant(): Unit = {
    val instant = Instant.now()
    println(s"instant = ${instant}")

    val zonedDateTime = ZonedDateTime.now()
    println(s"zonedDateTime instant = ${zonedDateTime.toInstant()}")
  }

  @Test
  @DisplayName("test Period")
  def testPeriod():Unit = {

    val localDate = LocalDate.of(2019, Month.APRIL, 15)
    val localDateNow = LocalDate.now()

    println(s"period between = ${Period.between(localDate, localDateNow)}")

    val oneYearPeriod = Period.ofYears(1)
    val oneYearAfter = LocalDate.now().plus(oneYearPeriod)
    
    println(s"oneYearAfter = ${oneYearAfter}")
  }

  @Test
  @DisplayName("test Duration")
  def testDuration():Unit = {
    val localTimeOf = LocalTime.of(22, 34)
    val localTimeNow = LocalTime.now()

    val duration = Duration.between(localTimeNow, localTimeOf)

    println(s"duration = ${duration}")
  }

  @Test
  @DisplayName("test temporal ChronoUnit")
  def testTemporalChronoUnit(): Unit = {
    val localDateNow = LocalDate.now()
    val localDateNextWeek = localDateNow.plus(1, ChronoUnit.WEEKS)
    val localDate2MonthsLater = localDateNow.plus(2, ChronoUnit.YEARS)

    println(s"localDateNow = ${localDateNow}")
    println(s"localDateNextWeek = ${localDateNextWeek}")
    println(s"localDate2MonthsLater = ${localDate2MonthsLater}")

    val localTime1 = LocalTime.of(22, 15)
    val localTime2 = LocalTime.of(15,12)

    println(s"between = ${ChronoUnit.MINUTES.between(localTime1, localTime2)}")
  }

  @Test
  @DisplayName("test localDateTimeSystem")
  def testLocalDateTimeSystem(): Unit = {
    val localDateTimeSystem = LocalDateTime.now()

    val zoneIdRome = ZoneId.of("Europe/Rome")
    val localDateTimeRome = LocalDateTime.now(zoneIdRome)

    println(s"between = ${ChronoUnit.MINUTES.between(localDateTimeRome, localDateTimeSystem)}")
  }

}
