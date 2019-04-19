package org.excavator.boot.logging

import java.util

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase

class StaticAppender extends AppenderBase[ILoggingEvent]{
  val events = new util.ArrayList[ILoggingEvent]()

  override def append(eventObject: ILoggingEvent): Unit = {
    events.add(eventObject)
  }

  def getEvents(): util.List[ILoggingEvent] = {
    events
  }

  def clearEvents(): Unit = {
    events.clear()
  }
}
