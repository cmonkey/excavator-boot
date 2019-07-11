package org.excavator.boot.concurrent.test

import java.util.concurrent.{Executors, Future}
import java.util.stream.{Collectors, Stream}

import org.excavator.boot.concurrent.LoginQueueUsingSemaphore
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._
import org.slf4j.LoggerFactory

class SemaphoreTest{
  val logger = LoggerFactory.getLogger(classOf[SemaphoreTest])

  @Test
  @DisplayName("test given Login Queue when Reach Limit then Blocked")
  def testGivenLoginQueueWhenReachLimitThenBlocked(): Unit = {

    val slots = 10

    val executorService = Executors.newFixedThreadPool(slots)

    val loginQueue = new LoginQueueUsingSemaphore(slots)

    executorService.submit[Boolean](() => loginQueue.tryLogin())

    val futures = Stream.generate(() => {
      executorService.submit[Boolean](() => loginQueue.tryLogin())
    }).limit(slots).collect(Collectors.toList[Future[Boolean]])

    for(i <- 0 until slots ){
      logger.info(s"v = ${futures.get(i).get}")
    }

    executorService.shutdown()

    assertEquals(0, loginQueue.availableSlots())

    assertFalse(loginQueue.tryLogin())
  }
}
