package org.excavator.boot.experiment.test

import jdk.incubator.foreign.{MemoryHandles, MemorySegment}
import org.junit.jupiter.api.{DisplayName, Test}

import java.nio.{ByteBuffer, ByteOrder}
import java.nio.channels.FileChannel
import java.nio.file.{Files, Path, Paths}

class MemorySegmentTest {

  @Test
  @DisplayName("testMemorySegment")
  def testMemorySegment(): Unit = {
    val memorySegment = MemorySegment.allocateNative(200)

    println(s"memorySegment = ${memorySegment}")

    val memorySegment2 = MemorySegment.ofByteBuffer(ByteBuffer.allocate(200))

    println(s"memorySegment2 = ${memorySegment2}")

    if(Files.exists(Paths.get("/tmp/DPI.log"))) {
      val mmap = MemorySegment.mapFile(Path.of("/tmp/DPI.log"), 200, 0, FileChannel.MapMode.READ_WRITE)

      println(s"mmap = ${mmap}")
    }

    val address = MemorySegment.allocateNative(100).address()

    println(s"address = ${address}")

    val value = 10:Long

    val memoryAddress = MemorySegment.allocateNative(8L).address()

    println(s"memoryAddress = ${memoryAddress}")

    val varHandle = MemoryHandles.varHandle(classOf[Long], ByteOrder.nativeOrder())

    println(s"varHandler = ${varHandle.toString}")

    //varHandle.set(memoryAddress, value)

    memorySegment.close()
  }

}
