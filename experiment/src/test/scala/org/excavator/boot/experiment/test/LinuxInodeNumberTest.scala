package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.LinuxInodeNumber
import org.junit.jupiter.api.{DisplayName, Test}

class LinuxInodeNumberTest {

  @Test
  @DisplayName("testGetLinuxInodeNumber")
  def testGetLinuxInodeNumber(): Unit = {
    val uri = "/tmp/tmux-1000/default"
    val inode = LinuxInodeNumber(uri)
    println(s"inode = ${inode}")

    assert(inode.toInt > 0)
  }

}
