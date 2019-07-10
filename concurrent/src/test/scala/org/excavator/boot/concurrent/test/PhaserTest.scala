package org.excavator.boot.concurrent.test

import java.util.concurrent.{Executors, Phaser}

import org.excavator.boot.concurrent.LongRunningAction
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class PhaserTest {

  @Test
  @DisplayName("test Phaser")
  def testPhaser(): Unit = {
    val executeService = Executors.newCachedThreadPool()

    val phaser = new Phaser(1)

    assertEquals(0, phaser.getPhase)

    executeService.submit(new LongRunningAction("thread-1", phaser))
    executeService.submit(new LongRunningAction("thread-2", phaser))
    executeService.submit(new LongRunningAction("thread-3", phaser))

    phaser.arriveAndAwaitAdvance()

    assertEquals(1, phaser.getPhase)

    executeService.submit(new LongRunningAction("thread-4", phaser))
    executeService.submit(new LongRunningAction("thread-5", phaser))

    phaser.arriveAndAwaitAdvance()

    assertEquals(2, phaser.getPhase)

    phaser.arriveAndDeregister()
  }

}
