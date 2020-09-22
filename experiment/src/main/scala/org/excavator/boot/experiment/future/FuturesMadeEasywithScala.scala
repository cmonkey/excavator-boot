package org.excavator.boot.experiment.future

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FuturesMadeEasywithScala extends App{

  Future{"Hi"}
  Future{"Hi"}.foreach(z => println(z + " world"))

  def job(n:Int) = Future{
    Thread.sleep(1000)
    println(n) // for demo only as this is side-effecting
    n + 1
  }

  val f = for{
    f1 <- job(1)
    f2 <- job(f1)
    f3 <- job(f2)
    f4 <- job(f3)
    f5 <- job(f4)
  }yield List(f1, f2, f3, f4, f5)

  f.map(z => println(s"Done. ${z.size} jobs run"))

  Thread.sleep(6000) // needed to prevent main thread from quitting
  // too early

  val fs = for{
    f1 <- job(1)
    f2 <- Future.sequence(List(job(f1), job(f1)))
    f3 <- job(f2.head)
    f4 <- Future.sequence(List(job(f3), job(f3)))
    f5 <- job(f4.head)
  }yield f2.size + f4.size

  fs.foreach(z => println(s"Done. $z jobs run is parallel"))

  Future{"abc".toInt}.map(z => z + 1)
  Future{"abc".toInt}.recover{case e => 0}.map(z => z + 1)
  Thread.sleep(10000)

}
