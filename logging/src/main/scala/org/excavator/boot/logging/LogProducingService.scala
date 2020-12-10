package org.excavator.boot.logging

import java.util.concurrent.Executors

import org.slf4j.LoggerFactory

class LogProducingService {

  private val logger = LoggerFactory.getLogger(classOf[LogProducingService])

  def writeSomeLoggingStatements(message:String): Unit = {
    logger.info(s"Let's assert some logs! $message")

    val executor = Executors.newSingleThreadExecutor()

    val future = executor.submit[Unit](() => logger.info("this message is in a separate thread"))

    do{

    }while(!future.isDone)
  }

}
