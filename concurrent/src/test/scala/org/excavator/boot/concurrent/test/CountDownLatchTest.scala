package org.excavator.boot.concurrent.test

import java.util
import java.util.concurrent.CountDownLatch
import java.util.stream.Collectors

import org.excavator.boot.concurrent.{WaitingWorker, Worker}
import org.junit.jupiter.api.{DisplayName, Test}
import org.slf4j.LoggerFactory
import org.assertj.core.api.Assertions._

class CountDownLatchTest {
  val logger = LoggerFactory.getLogger(classOf[CountDownLatchTest])

  @Test
  @DisplayName("test When Parallel Processing then MainThread Will BlockUntilCompletion")
  def testWhenParallelProcessingThenMainThreadWillBlockUntilCompletion(): Unit = {

    val outputScraper:util.List[String] = util.Collections.synchronizedList(new util.ArrayList[String])

    val countDownLatch = new CountDownLatch(5)

    val workers = java.util.stream.Stream.generate(() => new Thread(new Worker(outputScraper, countDownLatch)))
      .limit(5)
      .collect(Collectors.toList[Thread])

    workers.forEach(thread => thread.start())

    countDownLatch.await()

    outputScraper.add("Latch Released")

    assertThat[String](outputScraper).containsExactly(
      "Counted down",
      "Counted down",
      "Counted down",
      "Counted down",
      "Counted down",
      "Latch Released",
    )
  }

  @Test
  @DisplayName("test When Doing Lots Of ThreadsInParalled Then StartThemAtTheSameTime")
  def testWhenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime(): Unit = {

    val outputScraper = util.Collections.synchronizedList(new util.ArrayList[String]())

    val readyThreadCounter = new CountDownLatch(5)
    val callingThreadBlocker = new CountDownLatch(1)
    val completedThreadCounter = new CountDownLatch(5)

    val workers = util.stream.Stream.generate(() =>
    new Thread(new WaitingWorker(outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
      .limit(5)
      .collect(Collectors.toList[Thread])

    workers.forEach(thread => thread.start())

    readyThreadCounter.await()

    outputScraper.add("Workers ready")

    callingThreadBlocker.countDown()

    completedThreadCounter.await()

    outputScraper.add("Workers complete")

    assertThat[String](outputScraper).containsExactly("Workers ready",
      "Counted down",
      "Counted down",
      "Counted down",
      "Counted down",
      "Counted down",
      "Workers complete"
    )
  }
}
