package org.excavator.boot.logging

import java.util.concurrent.{Executor, Executors}

import org.slf4j.LoggerFactory

class OtherLogProductingService {

  val logger = LoggerFactory.getLogger(classOf[OtherLogProductingService])

  def writeSomeLoggingStatements(message: String) : Unit = {
    logger.info(s"Let's a assert some logs! ${message}")

    val executor = Executors.newSingleThreadExecutor()

    val future = executor.submit[Unit](() => logger.info("This message is in a separate thread"))

    do{

    }while(!future.isDone)
  }

}
