package org.excavator.boot.experiment.directed

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

}
