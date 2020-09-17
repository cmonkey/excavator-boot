package org.excavator.boot.experiment.monix
import monix.execution.Scheduler.Implicits.global
import monix.eval._
import monix.reactive._

class ParallelProcessing {

  def theNaiveWay(): Unit ={
    val items = 0 until 1000

    val tasks = items.map(i => Task(i * 2))

    val aggregate = Task.parSequence(tasks).map(_.toList)

    aggregate.foreach(println)
  }

}
