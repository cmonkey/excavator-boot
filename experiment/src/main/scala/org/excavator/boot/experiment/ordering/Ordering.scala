package org.excavator.boot.experiment.ordering

trait Ordering[A] {

  def compare(x:A, y:A):Int

}
