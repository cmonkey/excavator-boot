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

  def imposingAParallelismLimit():Unit = {
    val items = 0 until 1000
    val tasks = items.map(i => Task(i * 2))
    val batches = tasks.sliding(10, 10).map(b => Task.parSequence(b)).iterator.to(Iterator)

    val aggregate = Task.sequence(batches).map(_.flatten.toList)

    aggregate.foreach(println)
  }

}
