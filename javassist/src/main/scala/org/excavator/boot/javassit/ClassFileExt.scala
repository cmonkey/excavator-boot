package org.excavator.boot.javassit

import javassist.bytecode.{AccessFlag, ClassFile, FieldInfo}

class ClassFileExt {

  def createClass(className:String, fileName: String): Unit = {
    val classfile = new ClassFile(false, className, null)
    classfile.setInterfaces(Array("java.lang.Cloneable"))

    val fieldInfo = new FieldInfo(classfile.getConstPool, fileName, "I")
    fieldInfo.setAccessFlags(AccessFlag.PUBLIC)
    classfile.addField(fieldInfo)
  }

}
