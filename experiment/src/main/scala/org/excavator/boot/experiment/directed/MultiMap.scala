package org.excavator.boot.experiment.directed

import java.util.Collections

import scala.collection.mutable

class MultiMap {

  // not key, but not null values
  val map = new mutable.LinkedHashMap[Any, Any]

  def put(key: Any, value: Any) = {
    var set = map.get(key).asInstanceOf[mutable.LinkedHashSet[Any]]
    if(set.isEmpty){
      set = new mutable.LinkedHashSet[Any]
      map.put(key, set)
    }

    if(null != value){
      set.add(value)
    }
  }

  def get(key: Any) = {
    map.getOrElse(key, mutable.LinkedHashSet.empty[Any]).asInstanceOf[mutable.LinkedHashSet[Any]]
  }

  def keySet() = map.keySet

  def removeAll(key: Any):mutable.LinkedHashSet[Any] = {
    map.remove(key) match {
      case Some(value) => value.asInstanceOf[mutable.LinkedHashSet[Any]]
      case None => mutable.LinkedHashSet.empty[Any]
    }
  }

  def remove(key:Any, value:Any) = {
    val values = map.get(key).asInstanceOf[mutable.LinkedHashSet[Any]]
    if(null != values){
      values.remove(value)
    }
  }

  override def toString: String = map.toString()

}
