package org.excavator.boot.experiment.directed

import java.util

import org.apache.commons.lang3.builder.ReflectionToStringBuilder

import scala.beans.BeanProperty

case class Node(@BeanProperty id: Int){

  val neighbors = new util.ArrayList[Integer]()

  def addNeighbors(e: Int) = {
    neighbors.add(e)
  }

  override def toString: String = ReflectionToStringBuilder.toString(this)
}
