package org.excavator.boot.netty.enums

sealed class ResponseViewMode protected(name:String,ordinal:Int) extends java.lang.Enum[ResponseViewMode](name, ordinal)

object ResponseViewMode{
  val FULL = new ResponseViewMode("FULL", 0)

  val BODY = new ResponseViewMode("body", 1)
}

