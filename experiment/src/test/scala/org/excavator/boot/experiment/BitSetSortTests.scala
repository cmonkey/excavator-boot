package org.excavator.boot.experiment

import org.junit.jupiter.api.{DisplayName, Test}

import java.nio.file.Files

class BitSetSortTest {

  @Test
  @DisplayName("test generate file")
  def testGenerateFile():Unit = {
    val outputPath = Files.createTempFile("random", ".txt")
    GenerationFile.generateRandomNumbers(outputPath)
  }

}
