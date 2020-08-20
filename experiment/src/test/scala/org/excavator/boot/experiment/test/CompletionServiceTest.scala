package org.excavator.boot.experiment.test

import java.util.concurrent.{Callable, ExecutorCompletionService, Executors}

import org.junit.jupiter.api.{BeforeAll, DisplayName, Test}

import scala.collection.mutable.ListBuffer

class CompletionServiceTest {

  @Test
  @DisplayName("testCompletionService")
  def testCompletionService(): Unit = {

    val executor = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())
    val completionService = new ExecutorCompletionService[Map[String,String]](executor)

    val jobs = CompletionServiceTest.jobs.toList
    val size = jobs.size
    jobs.foreach(job => {
      completionService.submit(job)
    })

    for(i <- 0 until size){
      println(completionService.take().get())
    }
  }

}

object CompletionServiceTest{
  var jobs = new ListBuffer[Callable[Map[String,String]]]
  @BeforeAll
  def init(): Unit ={
    val longKey = "REF_SHARDING:risklvl:entity_name_joinRule_rsk_lvl_id:=:contact_joinRule_6482"
    val shortKey = "REF_SHARDING:risklvl:rsk_lvl_id:=:6482"
    jobs += Job(longKey)
    jobs += Job(shortKey)
  }

  case class Job(key:String) extends Callable[Map[String, String]]{
    override def call(): Map[String, String] = {
      Map(key -> "foo")
    }
  }
}
