package org.excavator.boot.logging

import java.util

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.slf4j.LoggerFactory

class ThreadSafeAppender extends AppenderBase[ILoggingEvent]{
  override def append(eventObject: ILoggingEvent): Unit = {

    var events = ThreadSafeAppender.threadLocal.get()

    if(null == events){
      events = new util.ArrayList[ILoggingEvent]()
      ThreadSafeAppender.threadLocal.set(events)
    }

    events.add(eventObject)

  }
}

object ThreadSafeAppender{
  val threadLocal = new ThreadLocal[java.util.List[ILoggingEvent]]

  def getEvents: util.List[ILoggingEvent] = {
    threadLocal.get()
  }

  def clearEvents() = {
    threadLocal.remove()
  }

  def isLogBackReady() = {
    try {
      val context = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
      true
    }catch{
      case e: ClassCastException => false
      case _:Throwable => false
    }
  }

  def pauseTillLogBackReady() = {
    do{

    }while(!isLogBackReady())
  }

}
