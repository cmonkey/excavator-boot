package org.excavator.boot.idworker.test

import org.excavator.boot.idworker.algorithm.Snowflake
import org.junit.Test
import org.junit.Assert._
class SnowflakeTest{

  @Test
  def testNextId() = {

    val snowflake = Snowflake.create(1)

    assertNotNull(snowflake)

    assertNotNull(snowflake.nextId())
    val size = 1000

    val ids = new Array[Long](size)

    assertEquals(size, ids.size)
  }
}
