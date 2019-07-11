package org.excavator.boot.concurrent

import java.util.concurrent.Semaphore

class LoginQueueUsingSemaphore(val slotLimit: Int) {

  val semaphore = new Semaphore(slotLimit)

  def tryLogin(): Boolean = {
    semaphore.tryAcquire()
  }

  def logout(): Unit = {
    semaphore.release()
  }

  def availableSlots(): Int = {
    semaphore.availablePermits()
  }

}
