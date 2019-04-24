package org.excavator.boot.javassit

import javassist.bytecode.{AccessFlag, ClassFile, FieldInfo}

class ClassFileExt {

  def createClass(className:String): Unit = {
    val classfile = new ClassFile(false, "org.excavator.boot.javassit."+className, null)
    classfile.setInterfaces(Array("java.lang.Cloneable"))

    val fieldInfo = new FieldInfo(classfile.getConstPool, "id", "I")
    fieldInfo.setAccessFlags(AccessFlag.PUBLIC)
    classfile.addField(fieldInfo)
  }

}
