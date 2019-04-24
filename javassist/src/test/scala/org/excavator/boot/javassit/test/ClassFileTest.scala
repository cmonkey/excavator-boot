package org.excavator.boot.javassit.test

import javassist.{ClassPool}
import org.excavator.boot.javassit.{ClassFileExt, ClassFileHelper}
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class ClassFileTest {

  @Test
  @DisplayName("testPointExt")
  def testPointExt() = {
    val className = "org.excavator.boot.javassit.test.Test"
    val fileName = "Id"

    val classFile = ClassFileExt.createClass(className, fileName)

    val classPool = ClassPool.getDefault

    val fields = ClassFileHelper.getFields(classPool, classFile)

    assertEquals(fields(0).getName , fileName)
  }

}
