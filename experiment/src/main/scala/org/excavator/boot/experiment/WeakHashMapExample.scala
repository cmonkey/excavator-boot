package org.excavator.boot.experiment

import java.util

object WeakHashMapExample extends App{

  val map = new util.WeakHashMap[Key,Project]()
  var key1 = Key("Active")
  val key2 = Key("inActive")

  map.put(key1, Project(100, "Customer Management System", "Customer Management System"))
  map.put(key2, Project(200, "Employee Management System", "Employee Management System"))

  key1 = null
  System.gc()

  map.entrySet().forEach(item => {
    println(item.getKey.key + " " + item.getValue)
  })

}

case class Key(key:String)
case class Project(num:Int, systemName:String, desc:String)
