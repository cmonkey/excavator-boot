package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.BigDecimalHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.{DisplayName, Test}


class BigDecimalHelperTest {

  @Test
  @DisplayName("test add bigDecimal ")
  def testAddBigDecimal():Unit = {
    val v1 = 12345678901234267.99
    val v2 = 98765432101234134.00
    val r = BigDecimalHelper.add(v1, v2)
    val expectedResult = BigDecimal.valueOf(111111111002468.99)
    assertEquals(expectedResult.toLong, r.toLong)
  }

}
