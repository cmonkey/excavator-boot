package org.excavator.boot.experiment.test

import scala.math._

class WithoutParams {

  def withoutParams(): Boolean = {
    val x = if (2 > 3) 5 else 7
    x == 7
  }

  def withParams(a:Int, b:Int):Boolean = {
    val y = if (a > b) 5 else 7
    y == 7
    y > 4
  }

  def doSomeMaths():Unit = {
    val x = max(5, 2 * min(7, abs(-3)))
    val y = sqrt(10)
    if(!(x == 6 && y > 3.0)){
      //
    }
  }

}
