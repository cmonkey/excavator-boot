package org.excavator.boot.experiment.directed

import java.util

import org.apache.commons.lang3.builder.ReflectionToStringBuilder

class Graph {
  var nodes:util.List[Node] = new util.ArrayList[Node]()

  def apply(nodes: util.List[Node]) = {
    val graph = new Graph
    graph.nodes = nodes

    graph
  }

  def addNode(e: Node) = {
    nodes.add(e)
  }

  def getNodes() = nodes

  def size  = nodes.size()

  def getNode(searchId: Int) = {
    nodes.stream().filter(node => node.id == searchId).findFirst()
  }

  override def toString: String = ReflectionToStringBuilder.toString(this)

}
