package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.directed.{Graph, Node}
import org.junit.jupiter.api.{DisplayName, Test}

class GraphTest {

  @Test
  @DisplayName("graph Init")
  def testGraphInit(): Unit = {

    val graph = new Graph
    val node1 = Node(1)
    val node2 = Node(2)
    val node3 = Node(3)

    node1.addNeighbors(2)
    node2.addNeighbors(3)
    node3.addNeighbors(4)

    graph.addNode(node1)
    graph.addNode(node2)
    graph.addNode(node3)

    println(graph)
  }

}
