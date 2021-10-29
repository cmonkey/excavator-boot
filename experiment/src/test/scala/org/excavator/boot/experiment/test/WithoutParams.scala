package org.excavator.boot.experiment.test

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

}
