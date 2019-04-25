package org.excavator.boot.instrumentation

import java.lang.instrument.Instrumentation

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.description.`type`.TypeDescription
import net.bytebuddy.dynamic.DynamicType
import net.bytebuddy.implementation.MethodDelegation
import net.bytebuddy.matcher.ElementMatchers
import net.bytebuddy.utility.JavaModule
import org.excavator.boot.instrumentation.interceptor.TimeInterceptor
import org.slf4j.LoggerFactory

class ByteBuddyAgent {

}

object ByteBuddyAgent{
  val logger = LoggerFactory.getLogger(classOf[ByteBuddyAgent])

  def transformer(instrumentation: Instrumentation) = {
    val transformer = new AgentBuilder.Transformer {
      override def transform(builder: DynamicType.Builder[_], typeDescription: TypeDescription, classLoader: ClassLoader, javaModule: JavaModule): DynamicType.Builder[_] = {
        builder.method(ElementMatchers.any())
          .intercept(MethodDelegation.to(classOf[TimeInterceptor]))
      }
    }

    val listener = new AgentBuilder.Listener {
      override def onDiscovery(s: String, classLoader: ClassLoader, javaModule: JavaModule, b: Boolean): Unit = ???

      override def onTransformation(typeDescription: TypeDescription, classLoader: ClassLoader, javaModule: JavaModule, b: Boolean, dynamicType: DynamicType): Unit = ???

      override def onIgnored(typeDescription: TypeDescription, classLoader: ClassLoader, javaModule: JavaModule, b: Boolean): Unit = ???

      override def onError(s: String, classLoader: ClassLoader, javaModule: JavaModule, b: Boolean, throwable: Throwable): Unit = ???

      override def onComplete(s: String, classLoader: ClassLoader, javaModule: JavaModule, b: Boolean): Unit = ???
    }

    new AgentBuilder.Default().`type`(ElementMatchers.nameStartsWith("org.excavator.boot"), ElementMatchers.any())
      .transform(transformer)
      .`with`(listener).installOn(instrumentation)
  }

  def tail(): Unit ={

  }

  def premain(agentArgs: String, instrumentation: Instrumentation) = {
    logger.info(s"ByteBuddyAgent premain param agentArgs = [{${agentArgs}]")
    transformer(instrumentation)

    tail()
  }

  def agentmain(agentArgs: String, instrumentation: Instrumentation) = {
    logger.info(s"ByteBuddyAgent agentmain param agentArgs = [{${agentArgs}]")

    transformer(instrumentation)

    tail()
  }

}
