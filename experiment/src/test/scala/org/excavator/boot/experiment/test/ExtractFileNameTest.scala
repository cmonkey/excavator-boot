package org.excavator.boot.experiment.test

import java.io.File
import java.nio.file.Path

import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class ExtractFileNameTest {

  @Test
  @DisplayName("testExtractFileName")
  def testExtractFileName() = {
    val extractFileName = "duke.html"
    val fqn  = "/tmp/" + extractFileName
    val file = new File(fqn)
    file.createNewFile()
    val actual = Path.of(fqn).getFileName.toString

    assertEquals(extractFileName, actual)
  }

}
