package org.excavator.boot.logging.test

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.{Calendar, Date, Locale, TimeZone}

import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._
import org.slf4j.LoggerFactory

class TestISO8601 {

  @Test
  @DisplayName("testISO-8601")
  def testISO8061(): Unit = {
    import TestISO8601._
    val currentTimeMillis:Long = 1577771671190L
    val date = new Date(currentTimeMillis)

    val calendar = Calendar.getInstance(TimeZone.getDefault, Locale.CHINA)
    calendar.setTime(date)

    val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    logger.info("simpleDateFormat = [{}]", simpleDateFormat.format(date))

    assertEquals(2019, calendar.get(Calendar.YEAR))

    val localDateTime = LocalDateTime.of(2019, 12 ,31, 14, 40, 3, 0)
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val localDateTimeFormat = dateTimeFormatter.format(localDateTime)
    logger.info("localDateTimeFormat = [{}]", localDateTimeFormat)

    assertEquals("2019", localDateTimeFormat.split("-").head)

  }

}

object TestISO8601{
  val logger = LoggerFactory.getLogger(classOf[TestISO8601])
}
