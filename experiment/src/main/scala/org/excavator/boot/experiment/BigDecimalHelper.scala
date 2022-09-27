package org.excavator.boot.experiment

object BigDecimalHelper {

  def add(v1:Double, v2:Double):BigDecimal = {
    val b1 = BigDecimal.valueOf(v1)
    val b2 = BigDecimal.valueOf(v2)
    b1 + b2
  }

}
