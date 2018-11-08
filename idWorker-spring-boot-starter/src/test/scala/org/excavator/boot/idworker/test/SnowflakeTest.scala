package org.excavator.boot.idworker.test

import org.excavator.boot.idworker.algorithm.Snowflake

object TestSnowflake extends App{

  val snowflake = Snowflake.create(1)

  println(s"id = ${snowflake.nextId()}")

  val groupName = "sss"
  val workerId = "bbb"
  println(s"groupName/workerId = /${groupName}/${workerId}")

  val size = 1000

  val ids = new Array[Long](size)

  println(ids.size)

  for(i <- 0 until size){
    ids(i) = 10L
  }

  println(ids.size)
}
