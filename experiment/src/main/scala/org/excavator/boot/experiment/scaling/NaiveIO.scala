package org.excavator.boot.experiment.scaling

class NaiveIO {

  private var dbCallCount: Int = 0

  def genericDbCall(seconds: Int) = {
    Thread.sleep(seconds * 1000)
    dbCallCount += 1
    println(s"Completed db call ${dbCallCount}")
  }

  def dbCall1() = {
    genericDbCall(1)
  }
  def dbCall2() = {
    genericDbCall(2)
  }
  def dbCall3() = {
    genericDbCall(3)
  }
}
