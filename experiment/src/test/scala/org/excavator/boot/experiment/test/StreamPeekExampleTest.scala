package org.excavator.boot.experiment.test

import org.assertj.core.util.Lists
import org.junit.jupiter.api.{DisplayName, Test}

import java.util.concurrent.{Executors, Future}

class StreamPeekExampleTest {

  @Test
  @DisplayName("test stream peek example")
  def testStreamPeek():Unit = {
    val nThreads = Runtime.getRuntime.availableProcessors()
    val executor = Executors.newFixedThreadPool(nThreads)

    val futures = Lists.newArrayList[Future[String]]()

    (0 to 1000).foreach(i =>{
      val f = executor.submit[String](() =>{
        s"${Thread.currentThread().getName} Hi $i"
      })
      futures.add(f)
    })

    val isAllDone = futures.stream().peek(f =>{
      val r = f.get()
      println(r)
    }).allMatch(f => f.isDone)
    println(isAllDone)
  }

}
