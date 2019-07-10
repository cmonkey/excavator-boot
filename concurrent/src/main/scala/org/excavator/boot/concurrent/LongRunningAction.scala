package org.excavator.boot.concurrent

import java.util.concurrent.Phaser

class LongRunningAction(val threadName: String,val phaser : Phaser) extends Runnable{

  phaser.register()

  override def run(): Unit = {
    phaser.arriveAndAwaitAdvance()

    Thread.sleep(20)

    phaser.arriveAndDeregister()

  }
}
