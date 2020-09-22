package org.excavator.boot.experiment.future

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FuturesMadeEasywithScala extends App{

  Future{"Hi"}
  Future{"Hi"}.foreach(z => println(z + " world"))

}
