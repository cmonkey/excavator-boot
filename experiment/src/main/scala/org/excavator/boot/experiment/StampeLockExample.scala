package org.excavator.boot.experiment

import java.util.concurrent.Executors
import java.util.concurrent.locks.StampedLock

object StampeLockExample extends App{

  val lock = new StampedLock()

  val b = new Balance(10000)

  val w:Runnable = () => {
    val stamp = lock.writeLock()
    b.setAmount(b.getAmount + 1000)
    println(s"Write: ${b.getAmount}")
    lock.unlockWrite(stamp)
  }

  val r:Runnable = () => {
    val stmp = lock.tryOptimisticRead()
    println(s"Read: ${b.getAmount}")
    lock.unlockRead(stmp)
  }

  val executor = Executors.newFixedThreadPool(10)
  for (i <- 1 to 50) {
    executor.submit(w)
    executor.submit(r)
  }
}
