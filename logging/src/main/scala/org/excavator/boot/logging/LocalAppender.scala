package org.excavator.boot.logging

import java.util

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.slf4j.LoggerFactory

class LocalAppender extends AppenderBase[ILoggingEvent]{
  start()


  override def append(eventObject: ILoggingEvent): Unit = {
    LocalAppender.events.add(eventObject)
  }

  def getEvents() = {
    LocalAppender.events
  }

  def clearEvents() = {
    LocalAppender.events.clear()
  }

}

object LocalAppender{

  val events = new util.ArrayList[ILoggingEvent]()

  def initialize(loggers:Array[String]): LocalAppender = {
    val localAppender = new LocalAppender

    for(loggerName <- loggers) {
      localAppender.setContext(LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext])
      val logger = LoggerFactory.getLogger(loggerName).asInstanceOf[Logger]
      logger.addAppender(localAppender)
    }

    localAppender
  }

  def isLogBackReady() = {

    try{
      val context = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
      true
    }catch{
      case e: ClassCastException => false
      case _ => false
    }

  }

  def pauseTillLogbackReady() = {
    do{

    }while(!isLogBackReady())
  }

  def cleanup(localAppender: LocalAppender) = {
    localAppender.stop()
    localAppender.clearEvents()
  }
}
