package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.{BitsetSort, GenerationFile, MeasureUtils}
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import java.nio.file.{Files, Paths}

class BitSetSortTests {

  @Test
  @DisplayName("test generate file")
  def testGenerateFile():Unit = {
    val outputPath = Files.createTempFile("random", ".txt")
    val measurementResult = MeasureUtils.measure(
      GenerationFile.generateRandomNumbers(outputPath)
    )
    println(s"程序执行时间:${measurementResult.executionTime / 1000000} 毫秒")
    println(s"程序内存消耗:${measurementResult.memoryConsumed} 字节")
  }

  @Test
  @DisplayName("test bitset sort")
  def testBitSetSort():Unit = {
    val outputPath = Files.createTempFile("random", ".txt")
    val measurementGenerateResult = MeasureUtils.measure(
      GenerationFile.generateRandomNumbers(outputPath)
    )
    println(s"生成数据的程序执行时间:${measurementGenerateResult.executionTime / 1000000} 毫秒")
    println(s"生成数据程序内存消耗:${measurementGenerateResult.memoryConsumed} 字节")

    val sortInputPath = outputPath
    val sortOutputPath = Files.createTempFile("random-bitset-sort",".txt")

    val measurementBitSetSortResult = MeasureUtils.measure(
      BitsetSort.sortFile(sortInputPath, sortOutputPath)
    )

    println(s"排序数据的程序执行时间:${measurementBitSetSortResult.executionTime / 1000000} 毫秒")
    println(s"排序数据程序内存消耗:${measurementBitSetSortResult.memoryConsumed} 字节")
  }

  @DisplayName("test bit set sort by file")
  @ParameterizedTest
  @ValueSource(strings = Array("/tmp/random4228685429583332152.txt"))
  def testBitSetSortByFile(inputFilePath:String):Unit = {
    println(s"inputFilePath is [${inputFilePath}]")
    val sortInputPath = Paths.get(inputFilePath)
    val isExists = sortInputPath.toFile.exists()
    if(isExists) {
      val sortOutputPath = Files.createTempFile("random-bitset-sort", ".txt")

      System.gc()

      val measurementBitSetSortResult = MeasureUtils.measure(
        BitsetSort.sortFile(sortInputPath, sortOutputPath)
      )

      println(s"排序数据后写入的文件:${sortOutputPath}")
      println(s"排序数据的程序执行时间:${measurementBitSetSortResult.executionTime / 1000000} 毫秒")
      println(s"排序数据程序内存消耗:${measurementBitSetSortResult.memoryConsumed} 字节")
    }
  }

}
