package org.excavator.boot.instrumentation

import java.lang.instrument.Instrumentation

import org.slf4j.LoggerFactory

class InstrumenetationHelper{

}

object InstrumenetationHelper {
  val logger = LoggerFactory.getLogger(classOf[InstrumenetationHelper])

  def transform(targetClass: Class[_], targetClassLoader: ClassLoader, instrumentation: Instrumentation) = {

    val dt = new AtmTransFormer(targetClass.getName, targetClassLoader)
    instrumentation.addTransformer(dt, true)
    try{
      instrumentation.retransformClasses(targetClass)
    }catch{
      case ex:Exception => throw new RuntimeException(s"Transform failed for: [${targetClass.getName}]", ex)
    }

  }

  def transformClass(className: String, instrumentation: Instrumentation): Any = {
    var targetClass: Class[_] = null
    var targetClassLoader:ClassLoader = null

    try{
      targetClass = Class.forName(className)
      targetClassLoader = targetClass.getClassLoader
      transform(targetClass, targetClassLoader, instrumentation)
      return
    }catch{
      case _ => logger.error("Class [{}] not found with Class.forName")
    }

    for(clazz <- instrumentation.getAllLoadedClasses){
      if(clazz.getName.equals(className)){
        targetClass = clazz
        targetClassLoader = targetClass.getClassLoader
        transform(targetClass, targetClassLoader, instrumentation)
        return
      }
    }

    throw new RuntimeException(s"Failed to find class [ ${className}]" )

  }

  def premain(agnetArgs: String, instrumentation: Instrumentation) = {
    logger.info("[Agent] in premain method")
    val className = ""
    transformClass(className, instrumentation)
  }

  def agentmain(agentArgs: String, instrumentation: Instrumentation) = {

    logger.info("[Agent] in agentmain method")
    val className = ""
    transformClass(className, instrumentation)
  }
}

