package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.{BitsetSort, GenerationFile, MeasureUtils}
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
    println(s"程序内存消耗:${measurementResult.memoryConsumed} MB")
  }

  @Test
  @DisplayName("test bitset sort")
  def testBitSetSort():Unit = {
    val outputPath = Files.createTempFile("random", ".txt")
    val measurementGenerateResult = MeasureUtils.measure(
      GenerationFile.generateRandomNumbers(outputPath)
    )
    println(s"生成数据的程序执行时间:${measurementGenerateResult.executionTime / 1000000} 毫秒")
    println(s"生成数据程序内存消耗:${measurementGenerateResult.memoryConsumed} MB")

    val sortInputPath = outputPath
    val sortOutputPath = Files.createTempFile("random-bitset-sort",".txt")

    val measurementBitSetSortResult = MeasureUtils.measure(
      BitsetSort.sortFile(sortInputPath, sortOutputPath)
    )

    println(s"排序数据的程序执行时间:${measurementBitSetSortResult.executionTime / 1000000} 毫秒")
    println(s"排序数据程序内存消耗:${measurementBitSetSortResult.memoryConsumed} MB")
  }

}
