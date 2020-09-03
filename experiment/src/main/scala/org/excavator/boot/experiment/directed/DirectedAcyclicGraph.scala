package org.excavator.boot.experiment.directed

import scala.collection.mutable

class DirectedAcyclicGraph {

  val fout = new MultiMap
  val fin = new MultiMap

  def hasPath(key: Any, value: Any):Boolean = {
    if(key == value){
      true
    }else{
      val set = fout.get(key)
      if(set.filter(elem => {hasPath(elem, value)}).nonEmpty){
        true
      }else{
        false
      }
    }

  }

  def addEdge(target:Any, origin:Any) = {
    if(hasPath(target, origin)){
      false
    }else{
      fout.put(origin, target)
      fout.put(target, null)

      fin.put(target, origin)
      fin.put(origin, null)

      true
    }
  }

  def addVertex(vertex: Any) = {
    fin.put(vertex, null)
    fout.put(vertex, null)
  }
  def removeVertex(vertex: Any) = {

    val targets = fout.removeAll(vertex)
    targets.foreach(elem => {
      fin.remove(elem,vertex)
    })
    val origins = fin.removeAll(vertex)
    origins.foreach(elem => {
      fout.remove(elem, vertex)
    })
  }

  def getSources() = {
    computeZeroEdgeVertices(fin)
  }
  def getSinks() = {
    computeZeroEdgeVertices(fout)
  }
  def computeZeroEdgeVertices(map: MultiMap) = {
    val candidates = map.keySet()
    val roots = new mutable.LinkedHashSet[Any]()
    candidates.foreach(key => {
      if(map.get(key).isEmpty){
        roots.add(key)
      }
    })

    roots
  }
  def getChildren(vertex:Any)={
    fout.get(vertex)
  }

}
