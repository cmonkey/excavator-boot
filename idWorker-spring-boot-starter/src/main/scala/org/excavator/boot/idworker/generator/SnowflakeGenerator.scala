package org.excavator.boot.idworker.generator

import org.excavator.boot.idworker.algorithm.Snowflake
import org.excavator.boot.idworker.storage.SnowflakeNodeRegister

class SnowflakeGenerator(val storage: SnowflakeNodeRegister) extends IdGenerator {

  var snowFlake: Snowflake = _

  def init() = {
    val workerId = storage.register()

    if(workerId >= 0){
      snowFlake = Snowflake.create(workerId)
    }else{
      throw new RuntimeException("workerId call exception")
    }
  }

  override def nextId = {
    snowFlake.nextId()
  }

  override def nextId(size: Int) = {
    snowFlake.nextId(size)
  }
}
