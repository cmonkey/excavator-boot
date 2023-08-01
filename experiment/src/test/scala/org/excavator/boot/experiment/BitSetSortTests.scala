package org.excavator.boot.experiment

import org.junit.jupiter.api.{DisplayName, Test}

import java.nio.file.Files

class BitSetSortTests {

  @Test
  @DisplayName("test generate file")
  def testGenerateFile():Unit = {
    val outputPath = Files.createTempFile("random", ".txt")
    val measurementResult = MeasureUtils.measure(
      GenerationFile.generateRandomNumbers(outputPath)
    )
    println(s"程序执行时间:${measurementResult.executionTime / 1000000} 毫秒")
    println(s"程序内存消耗:${measurementResult.memoryConsumed.toDouble / (1024 * 1024) } MB")
  }

}
