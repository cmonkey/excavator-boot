package org.excavator.boot.experiment

import monix.bio.IO

import monix.execution.Scheduler.Implicits.global

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class MonixIOscheduler {

  def run(msg:String) = {

    val bio = IO{println("Hello, World!"); msg}

    val scalaFuture: Future[String] = bio.runToFuture

    val result:String = Await.result(scalaFuture, 5.seconds)

    result

  }

}
