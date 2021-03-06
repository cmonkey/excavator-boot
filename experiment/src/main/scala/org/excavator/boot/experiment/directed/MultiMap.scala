package org.excavator.boot.experiment.directed

import java.util.Collections

import scala.collection.mutable

class MultiMap {

  // not key, but not null values
  val map = new mutable.LinkedHashMap[Any, Any]

  def put(key: Any, value: Any) = {
    map.get(key) match {
      case Some(set:mutable.LinkedHashSet[Any]) => {
        if(null != value){
          set.add(value)
        }
      }
      case None => {
        val set = new mutable.LinkedHashSet[Any]
        set.add(value)
        map.put(key, set)
      }
    }
  }

  def get(key: Any) = {
    map.get(key) match {
      case Some(v) => {
        v.asInstanceOf[mutable.LinkedHashSet[Any]]
      }
      case None => {
        mutable.LinkedHashSet.empty[Any]
      }
    }
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
