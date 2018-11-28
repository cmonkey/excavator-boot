package org.excavator.boot.idworker.test

import org.excavator.boot.common.helper.JSONProxy
import org.excavator.boot.idworker.algorithm.Snowflake
import org.excavator.boot.idworker.storage.SnowflakeNodeInfo
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

  @Test
  def serializer(): Unit ={
    val info = new SnowflakeNodeInfo(1)
    val s = JSONProxy.toJSONString(info.toString)
    println(s"${s}")
  }
}
