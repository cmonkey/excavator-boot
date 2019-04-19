package org.excavator.boot.logging

import java.util

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.slf4j.{Logger, LoggerFactory}

class LocalAppender extends AppenderBase[ILoggingEvent]{
  start()


  override def append(eventObject: ILoggingEvent): Unit = {

  }
}

object LocalAppender{

  val events = new util.ArrayList[ILoggingEvent]()

  def initialize(loggers:String*): LocalAppender = {
    val localAppender = new LocalAppender

    for(loggerName <- loggers) {
      localAppender.setContext(LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext])
      val logger = LoggerFactory.getLogger(loggerName).asInstanceOf[Logger]
      logger.
    }
  }
}
