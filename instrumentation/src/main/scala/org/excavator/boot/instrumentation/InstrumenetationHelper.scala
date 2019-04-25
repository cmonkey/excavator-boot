package org.excavator.boot.instrumentation

import java.lang.instrument.Instrumentation

import org.slf4j.LoggerFactory

class InstrumenetationHelper{

}

object InstrumenetationHelper {
  val logger = LoggerFactory.getLogger(classOf[InstrumenetationHelper])

  def transformClass(className: String, instrumentation: Instrumentation) = {

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

