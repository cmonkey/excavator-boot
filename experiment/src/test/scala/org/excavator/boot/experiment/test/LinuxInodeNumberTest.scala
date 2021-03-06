package org.excavator.boot.experiment.test

import java.nio.file.Files

import org.excavator.boot.experiment.LinuxInodeNumber
import org.junit.jupiter.api.{DisplayName, Test}

class LinuxInodeNumberTest {

  @Test
  @DisplayName("testGetLinuxInodeNumber")
  def testGetLinuxInodeNumber(): Unit = {
    val uri = Files.createTempFile("test", ".bin").toAbsolutePath.toString
    val inode = LinuxInodeNumber(uri)
    println(s"inode = ${inode}")

    assert(inode.toInt > 0)
  }

}
