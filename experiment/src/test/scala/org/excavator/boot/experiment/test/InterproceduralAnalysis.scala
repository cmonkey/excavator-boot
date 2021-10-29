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

  private def absoluteDifferencePlusOffset(x: Int, y:Int, offset:Int = 5): Int = {
    if (y > x) y - x + offset else x - y + offset
  }

  def analyzeV2(): Unit = {
    val x = absoluteDifferencePlusOffset(y = 15, x = 12) + 
    absoluteDifferencePlusOffset(y = 2, x = 9, offset = 10)
    if (x == 25){
      println(x)
    }
  }


}
