package org.excavator.boot.concurrent

import java.time.Instant
import java.util.concurrent.CountDownLatch

import org.slf4j.LoggerFactory

class Worker(val outputScraper: List[String], val countDownLatch: CountDownLatch) extends Runnable {

  val logger = LoggerFactory.getLogger(classOf[Worker])

  def doSomeWork(): Unit = {
    logger.info(s"doSomeWork ${Instant.now()}")
  }
  override def run(): Unit = {
    doSomeWork()
    outputScraper + "Counted down"
    countDownLatch.countDown();
  }
}
