package org.excavator.boot.netty.enums

sealed class State protected(name:String, ordinal: Int) extends java.lang.Enum[State](name, ordinal)

object State{
  val WAITING = new State("waiting", 0)
  val DONE = new State("done", 1)
}
