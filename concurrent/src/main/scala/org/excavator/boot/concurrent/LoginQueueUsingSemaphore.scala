package org.excavator.boot.concurrent

import java.util.concurrent.Semaphore

import org.slf4j.LoggerFactory

class LoginQueueUsingSemaphore(val slotLimit: Int) {

  val logger = LoggerFactory.getLogger(classOf[LoginQueueUsingSemaphore])

  val semaphore = new Semaphore(slotLimit)

  def tryLogin(): Boolean = {
    logger.info(s"thread name = ${Thread.currentThread().getName} and availableSlots = ${availableSlots()}")
    semaphore.tryAcquire()
  }

  def logout(): Unit = {
    semaphore.release()
  }

  def availableSlots(): Int = {
    semaphore.availablePermits()
  }

}
