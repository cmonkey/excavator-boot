package org.excavator.boot.experiment.test

class InterproceduralAnalysis {

  private def absoluteDifference(x: Int, y:Int):Int = {
    if(y > x) y - x else x - y
  }

  def analyze():Unit = {
    val x = absoluteDifference(12, 15) + absoluteDifference(9, 2)
    if (x == 10){
      println(x)
    }
  }

}
