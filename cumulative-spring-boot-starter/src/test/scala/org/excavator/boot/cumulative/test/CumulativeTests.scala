package org.excavator.boot.cumulative.test

import javax.annotation.Resource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[CumulativeApplication]))
class CumulativeTests {
  @Resource
  private[test] val cumulativeService:CumulativeService = null

  @Test def testCumulativeByDay(): Unit = {
    val key = "day"
    val dimensionKey = "testDay"
    val value = "10"
    val r = cumulativeService.cumulativeByDay(key, dimensionKey, value)
    assertTrue(java.lang.Long.valueOf(r) >= java.lang.Long.valueOf(value))
  }

  @Test def testCumulativeByMonth(): Unit = {
    val key = "month"
    val dimensionKey = "testMonth"
    val value = "10"
    val r = cumulativeService.cumulativeByMonth(key, dimensionKey, value)
    assertTrue(java.lang.Long.valueOf(r) >= java.lang.Long.valueOf(value))
  }

  @Test def testCumulativeByDayAnMonth(): Unit = {
    val key = "dayAndMonth"
    val dimensionKey = "testDayAndMonth"
    val value = "10"
    cumulativeService.cumulativeByDayAndMonth(key, dimensionKey, value)
    val dayValue = cumulativeService.queryByDay(key, dimensionKey)
    val monthValue = cumulativeService.queryByMonth(key, dimensionKey)
    assertTrue(java.lang.Long.valueOf(dayValue) >= java.lang.Long.valueOf(value))
    assertTrue(java.lang.Long.valueOf(monthValue) >= java.lang.Long.valueOf(value))
  }

  @Test def testCumulativeByYear(): Unit = {
    val key = "year"
    val dimensionKey = "testYear"
    val value = "10"
    var r = cumulativeService.cumulativeByYear(key, dimensionKey, value)
    assertTrue(java.lang.Long.valueOf(r) >= java.lang.Long.valueOf(value))
    r = cumulativeService.queryByYear(key, dimensionKey)
    assertTrue(java.lang.Long.valueOf(r) >= java.lang.Long.valueOf(value))
  }
}
