package org.excavator.boot.experiment

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Paths}

class LinuxInodeNumber {

  def getLinuxInodeNumber(uri:String) = {
    val path = Paths.get(uri)
    val attr = Files.readAttributes(path, classOf[BasicFileAttributes])
    val fileKey = attr.fileKey()
    val s = fileKey.toString()
    val inode = s.substring(s.indexOf("ino=") + 4, s.indexOf(")"))
    inode
  }

}

object LinuxInodeNumber{
  def apply(path:String) = {
    new LinuxInodeNumber().getLinuxInodeNumber(path)
  }
}
