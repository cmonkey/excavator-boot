package org.excavator.boot.experiment.future

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class TutorialFuture {

  def dountStock(donut:String):Future[Int] = Future {
    // assume some long running database operation
    println("checking donut stock")
    10
  }

}
