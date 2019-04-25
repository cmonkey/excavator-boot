package org.excavator.boot.instrumentation.application

import org.slf4j.LoggerFactory

class MyAtm {
}

object MyAtm{
  val account = 10
  val logger = LoggerFactory.getLogger(classOf[MyAtm])
  def withdrawMoney(amount: Int) = {
    Thread.sleep(20001)
    logger.info(s"[Application] successful withdrawal of [{}] units ${amount}")
  }
}
