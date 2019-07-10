package org.excavator.boot.concurrent

import java.util.concurrent.Phaser

import org.slf4j.LoggerFactory

class LongRunningAction(val threadName: String,val phaser : Phaser) extends Runnable{
  val logger = LoggerFactory.getLogger(classOf[LongRunningAction])

  phaser.register()

  override def run(): Unit = {
    logger.info(s"This is phaser = ${phaser.getPhase} thread name ${threadName} before long running action")
    phaser.arriveAndAwaitAdvance()

    Thread.sleep(20)

    phaser.arriveAndDeregister()

  }
}
