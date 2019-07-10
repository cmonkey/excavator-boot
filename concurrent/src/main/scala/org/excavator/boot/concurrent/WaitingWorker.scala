package org.excavator.boot.concurrent

import java.util
import java.util.concurrent.CountDownLatch

import org.slf4j.LoggerFactory

class WaitingWorker(val outputScraper: util.List[String],
                    val readyThreadCounter: CountDownLatch,
                    val callingThreadBlocker: CountDownLatch,
                    val completedThreadCounter: CountDownLatch) extends Runnable{

  val logger = LoggerFactory.getLogger(classOf[WaitingWorker])

  def doSomeWork(): Unit = {
    logger.info(s"thread name = ${Thread.currentThread().getName} now = ${java.time.Instant.now()}")
  }

  override def run(): Unit = {
    readyThreadCounter.countDown()

    callingThreadBlocker.await()

    doSomeWork()

    outputScraper.add("Counted down")

    logger.info(s"thread name = ${Thread.currentThread().getName} outputScraper = ${outputScraper}")

    completedThreadCounter.countDown()
  }
}
