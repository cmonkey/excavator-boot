package org.excavator.boot.experiment.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.{DisplayName, Test}

import java.nio.ByteBuffer

class ByteBufferTest {

  @Test
  @DisplayName("test buffer")
  def testBuffer(): Unit = {
    val buffer = ByteBuffer.wrap(Array[Byte](1,2,3))
    buffer.position(1)
    assertEquals(2, buffer.get)
  }
}
