package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.scaling.NaiveIO
import org.junit.jupiter.api.{DisplayName, Test}

class NaiveIOTest {

  @Test
  @DisplayName("test naiveIO")
  def testNaiveIO(): Unit = {
    val startTime = System.currentTimeMillis()
    val naiveIO = new NaiveIO
    for(i <- 0 until 5){
      naiveIO.dbCall1()
      naiveIO.dbCall2()
      naiveIO.dbCall3()
    }

    println(s"complete IO calls in ${System.currentTimeMillis() - startTime} ms")
  }

}
