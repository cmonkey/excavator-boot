package org.excavator.boot.experiment.test

import java.nio.file.{Files, Path}

import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class ExtractFileName {

  @Test
  @DisplayName("testExtractFileName")
  def testExtractFileName() = {
    val extractFileName = "duke.html"
    val fqn  = "/tmp/another/" + extractFileName
    Files.createFile(Path.of(fqn))
    val actual = Path.of(fqn).getFileName.toString

    assertEquals(extractFileName, actual)
  }

}
