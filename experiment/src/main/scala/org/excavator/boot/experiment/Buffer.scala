package org.excavator.boot.experiment

import java.util.concurrent.LinkedBlockingDeque

class Buffer {

  private val MAX_SIZE = 10

  private val queue = new LinkedBlockingDeque[Integer](MAX_SIZE)

  def addItem(item:Int) = {
    println(Thread.currentThread() + ": Adding item: " + item)
    queue.put(item)
  }

  def getItem(): Int = {
    println(Thread.currentThread() + ": Getting item")
    queue.take()
  }

}
