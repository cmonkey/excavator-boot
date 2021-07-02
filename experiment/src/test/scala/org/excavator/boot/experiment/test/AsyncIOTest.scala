package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.scaling.AsyncIO
import org.junit.jupiter.api.{DisplayName, Test}

import java.util.concurrent.{CompletableFuture, ForkJoinPool, TimeUnit}

class AsyncIOTest {

  @Test
  @DisplayName("Async in action!")
  def testAsyncIO(): Unit = {
    val asyncIo = new AsyncIO
    val startTime = System.currentTimeMillis()

    for(i <- 0 until 5){
      CompletableFuture.runAsync(() => {
        asyncIo.dbCall1()
      })
      CompletableFuture.runAsync(() => {
        asyncIo.dbCall2()
      })
      CompletableFuture.runAsync(() => {
        asyncIo.dbCall3()
      })
    }
    ForkJoinPool.commonPool().awaitTermination(2, TimeUnit.MINUTES)
    println(s"completed IO calls in ${System.currentTimeMillis() - startTime} ms")
  }

}
