package org.excavator.boot.javassit.test

import org.excavator.boot.javassit.ClassFileExt
import org.junit.jupiter.api.{DisplayName, Test}

class ClassFileExtTest {

  @Test
  @DisplayName("testPointExt")
  def testPointExt() = {
    val classFileExt = new ClassFileExt
    classFileExt.createClass("Test")
  }

}
