package org.excavator.boot.experiment.directed

import java.util

import scala.beans.BeanProperty

case class Node(@BeanProperty id: Int){

  val neighbors = new util.ArrayList[Integer]()

  def addNeighbors(e: Int) = {
    neighbors.add(e)
  }
}
