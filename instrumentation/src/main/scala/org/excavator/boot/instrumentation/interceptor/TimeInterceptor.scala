package org.excavator.boot.instrumentation.interceptor

import java.lang.reflect.Method
import java.util.concurrent.Callable

import net.bytebuddy.implementation.bind.annotation.{Origin, RuntimeType, SuperCall}
import org.excavator.boot.instrumentation.annoation.TraceTime
import org.slf4j.LoggerFactory

class TimeInterceptor {

}

object TimeInterceptor{
  val logger = LoggerFactory.getLogger(classOf[TimeInterceptor])

  @RuntimeType
  def interceptor(@Origin clazz: Class[_],
                 @Origin method: Method,
                 @SuperCall callable: Callable[_]) = {

    logger.info(s"TimeInterceptor interceptor clazz = [{${clazz}] method = [{${method}] callable = [{${callable}]")
    val traceTime = method.getAnnotation(classOf[TraceTime])

    if(null == traceTime){
      callable.call()
    }else{
      val start = System.currentTimeMillis()
      try{
        callable.call()
      }catch{
        case ex:Throwable => logger.error(s"traceTime interceptor Exception = ${ex}", ex)
      }finally {
        logger.info(s"clazz simpleName = [{${clazz.getSimpleName}}] methodName = [{${method.getName}}] cost = [{${System.currentTimeMillis() - start}] ms")
      }
    }
  }
}
