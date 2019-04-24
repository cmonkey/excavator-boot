package org.excavator.boot.javassit;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.bytecode.ClassFile;

import java.lang.reflect.Field;

public class ClassFileHelper {

    public static Field[] getFields(ClassPool classPool, ClassFile classFile) throws CannotCompileException {

        return classPool.makeClass(classFile).toClass().getFields();
    }
}
