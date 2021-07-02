package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.scaling.ScalingIO
import org.junit.jupiter.api.{DisplayName, Test}

import java.util.concurrent.{CompletableFuture, Executors, ForkJoinPool, TimeUnit}

class ScalingIOTest {
  @Test
  @DisplayName("test naiveIO")
  def testNaiveIO(): Unit = {
    val startTime = System.currentTimeMillis()
    val naiveIO = new ScalingIO
    for(i <- 0 until 5){
      naiveIO.dbCall1()
      naiveIO.dbCall2()
      naiveIO.dbCall3()
    }

    println(s"complete Naive IO calls in ${System.currentTimeMillis() - startTime} ms")
  }

  @Test
  @DisplayName("Async in action!")
  def testAsyncIO(): Unit = {
    val asyncIo = new ScalingIO
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
    println(s"completed Async IO calls in ${System.currentTimeMillis() - startTime} ms")
  }

  @Test
  @DisplayName("test Async with dedicated ThreadPool")
  def testAsyncWithDediatedThreadPool():Unit = {
    val scalableIO = new ScalingIO 
    val startTime = System.currentTimeMillis()

    val executor = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())

    for(i <- 0 until 5){
      CompletableFuture.runAsync(() => {
        scalableIO.dbCall1()
      }, executor)
      CompletableFuture.runAsync(() => {
        scalableIO.dbCall1()
      }, executor)
      CompletableFuture.runAsync(() => {
        scalableIO.dbCall1()
      }, executor)
    }

    executor.shutdown()
    executor.awaitTermination(2, TimeUnit.MINUTES)
    println(s"Completed IO calls in ${System.currentTimeMillis() - startTime}")
  }
}
