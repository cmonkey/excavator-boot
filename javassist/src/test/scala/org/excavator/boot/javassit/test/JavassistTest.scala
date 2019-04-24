package org.excavator.boot.javassit.test

import java.lang.reflect.Field
import java.util
import java.util.stream.Collectors

import javassist.ClassPool
import javassist.bytecode.{AccessFlag, Bytecode, FieldInfo, MethodInfo, Mnemonic}
import org.excavator.boot.javassit.{ClassFileExt, ClassFileHelper}
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

import scala.collection.mutable

class JavassistTest {

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

  @Test
  @DisplayName("testLoadByteCode")
  def testLoadByteCode() = {
    val classPool = ClassPool.getDefault
    val classFile = classPool.get("org.excavator.boot.javassit.Point").getClassFile

    val methodInfo = classFile.getMethod("move")
    val codeAttribute = methodInfo.getCodeAttribute
    val codeIterator = codeAttribute.iterator()

    val operations = new java.util.LinkedList[String]()

    while(codeIterator.hasNext){
      val index = codeIterator.next()
      val op = codeIterator.byteAt(index)
      operations.add(Mnemonic.OPCODE(op))
    }

    operations.forEach(operation => println(operation))

    assertEquals(operations, util.Arrays.asList("aload_0", "iload_1", "putfield", "aload_0", "iload_2", "putfield", "return"))
  }

  @Test
  @DisplayName("testAddFieldToExistingClassBytecode")
  def testAddFieldToExistingClassBytecode() = {
    val fieldName = "id"
    val classPool = ClassPool.getDefault
    val classFile = classPool.get("org.excavator.boot.javassit.Point").getClassFile

    val fieldInfo = new FieldInfo(classFile.getConstPool, fieldName, "I")
    fieldInfo.setAccessFlags(AccessFlag.PUBLIC)
    classFile.addField(fieldInfo)

    val fields = ClassFileHelper.getFields(classPool, classFile)

    val fieldsList = new util.ArrayList[String]()

    util.stream.Stream.of(fields:_*).forEach(field => {
      fieldsList.add(field.getName)
    })

    assertTrue(fieldsList.contains(fieldName))

  }

  @Test
  @DisplayName("testAddingConstructorToClassBytecode")
  def testAddingConstructorToClassBytecode() = {
    val classPool = ClassPool.getDefault
    val classFile = classPool.get("org.excavator.boot.javassit.Point").getClassFile

    val bytecode = new Bytecode(classFile.getConstPool)

    bytecode.addAload(0);
    bytecode.addInvokespecial("java/lang/Object", MethodInfo.nameInit, "()/V")
    bytecode.addReturn(null)

    val methodInfo = new MethodInfo(classFile.getConstPool, MethodInfo.nameInit, "()/V")
    methodInfo.setCodeAttribute(bytecode.toCodeAttribute)

    classFile.addMethod(methodInfo)

    val codeIterator = bytecode.toCodeAttribute.iterator()

    val operations = new util.LinkedList[String]()

    while(codeIterator.hasNext){
      val index = codeIterator.next()
      val op = codeIterator.byteAt(index)

      operations.add(Mnemonic.OPCODE(op))
    }

    assertEquals(operations, util.Arrays.asList("aload_0", "invokespecial", "return"))
  }

}
