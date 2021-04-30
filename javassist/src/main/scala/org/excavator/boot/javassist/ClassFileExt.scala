package org.excavator.boot.javassist

import javassist.bytecode.{AccessFlag, ClassFile, FieldInfo}

object ClassFileExt {

  def createClass(className:String, fileName: String): ClassFile = {
    val classfile = new ClassFile(false, className, null)
    classfile.setInterfaces(Array("java.lang.Cloneable"))

    val fieldInfo = new FieldInfo(classfile.getConstPool, fileName, "I")
    fieldInfo.setAccessFlags(AccessFlag.PUBLIC)
    classfile.addField(fieldInfo)

    classfile
  }

}
