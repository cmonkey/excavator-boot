package org.excavator.boot.experiment.scaling

class AsyncIO {

  private var dbCallCount: Int = 0

  def genericeDbCall(seconds:Int) = {
    Thread.sleep(seconds * 1000)
    dbCallCount += 1
    println(s"Completed db call ${dbCallCount}")
  }

  def dbCall1() = {
    genericeDbCall(1)
  }

  def dbCall2() = {
    genericeDbCall(2)
  }

  def dbCall3() = {
    genericeDbCall(3)
  }
}
