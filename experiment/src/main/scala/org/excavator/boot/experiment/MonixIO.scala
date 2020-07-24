package org.excavator.boot.experiment

import monix.bio.{IO, UIO}
import monix.execution.CancelableFuture
import scala.concurrent.duration._

import monix.execution.Scheduler.Implicits.global

case class TypedError(i: Int)

object MonixIO{

  def run() = {

    val taskA: UIO[Int] = IO.now(10)
      .delayExecution(2.seconds)
      .doOnCancel(UIO(println("taskA hash been cancelled")))

    val taskB: IO[TypedError, Int] = IO.raiseError(TypedError(-1))
      .delayExecution(1.second)
      .guarantee(UIO(println("taskB hash finished")))

    val t: IO[TypedError, Int] = IO.race(taskA, taskB).map {
      case Left(value) => value * 10
      case Right(value) => value * 20
    }

    val handled: UIO[Int] = t.onErrorHandle {
      case TypedError(i) => i
    }

    val f: CancelableFuture[Int] = handled.runToFuture

  }

}
