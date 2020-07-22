package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.NestedFutureBlocks._
import org.junit.jupiter.api.{DisplayName, Test}

import scala.concurrent.Await
import scala.concurrent.duration.Duration.Inf

class NestedFutureBlocksTest {

  @Test
  @DisplayName("test Scala 2.13 by Nested Future Blocks ")
  def testNestedFutureBlocks() = {
    val r = Await.result(runAsyncSerial(), Inf)
    println(r)
  }

}
