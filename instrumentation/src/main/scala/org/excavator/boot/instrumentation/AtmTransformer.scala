package org.excavator.boot.instrumentation

import java.io.IOException
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

import javassist.{CannotCompileException, ClassPool, CtClass, NotFoundException}
import org.slf4j.LoggerFactory

class AtmTransformer(val targetClassName: String, val targetClassLoader: ClassLoader) extends ClassFileTransformer{
  val logger = LoggerFactory.getLogger(classOf[AtmTransformer])
  val WITHDRAW_MONEY_METHOD = "withdrawMoney"

  override def transform(loader: ClassLoader, className: String, classBeingRedefined: Class[_], protectionDomain: ProtectionDomain, classfileBuffer: Array[Byte]): Array[Byte] = {

    var byteCode = classfileBuffer
    val finalTargetClassName = targetClassName.replaceAll("\\.", "/")

    if(!className.equals(finalTargetClassName)){
      logger.info(s"className not equals finalTargetClassName in className = ${className} finalTargetClassName = ${finalTargetClassName}")
      byteCode
    }else{
      if(className.equals(finalTargetClassName) && loader.equals(targetClassLoader)){
        logger.info("[Agent] Transforming class AtmTransformer")

        try{
          val classPool = ClassPool.getDefault
          val ctClass = classPool.get(targetClassName)
          val ctMethod = ctClass.getDeclaredMethod(WITHDRAW_MONEY_METHOD)

          ctMethod.addLocalVariable("startTime", CtClass.longType)
          ctMethod.insertBefore("startTime = System.currentTimeMillis();")

          val endBlock = new StringBuilder

          ctMethod.addLocalVariable("endTime", CtClass.longType)
          ctMethod.addLocalVariable("opTime", CtClass.longType)

          endBlock.append("endTime = System.currentTimeMillis();")
          endBlock.append("opTime = (endTime - startTime / 1000);")

          endBlock.append("logger.info(\"[Application] Withdrawal operation completed in:\" + opTime + \" seconds!\");")
          ctMethod.insertAfter(endBlock.toString())

          byteCode = ctClass.toBytecode
          ctClass.detach()
        }catch{
          case ex: NotFoundException => logger.error(s"Exception ${ex}")
          case ex: CannotCompileException => logger.error(s"Exception ${ex}", ex)
          case ex: IOException  => logger.error(s"Exception ${ex}")
        }
      }

      byteCode
    }

  }
}
