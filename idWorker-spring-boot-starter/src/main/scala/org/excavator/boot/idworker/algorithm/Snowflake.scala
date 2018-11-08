package org.excavator.boot.idworker.algorithm

import org.joda.time.{DateTime, format}
import org.joda.time.format.DateTimeFormat
import org.slf4j.LoggerFactory
import org.slf4j.Logger

class Snowflake private(id: Long){

  val logger: Logger = LoggerFactory.getLogger(classOf[Snowflake])

  var workerId: Long = -1L

  val fmt: format.DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
  val dateTime: DateTime = fmt.parseDateTime("2017-01-01 00:00:00")

  val epoch: Long = dateTime.getMillis

  val workerIdBits = 10L

  val sequenceBits = 12L

  val maxWorkerId = -1L ^ -1L << this.workerIdBits

  val workerIdShift = this.sequenceBits

  val timestampLeftShift = sequenceBits + workerIdBits

  val sequenceMask = -1L ^ -1L << sequenceBits

  var sequence = 0L

  var lastTimestamp = -1L

  if(id > maxWorkerId ||  id < 0){
      val message = String.format("worker id grater than %d or less than 0", Array(maxWorkerId))
      throw new IllegalArgumentException(message)
  }else{
      workerId = id
  }

  def nextId(size: Int): Array[Long] = {
    logger.info("nextId param size = {}", size)

    if(size <= 0 || size > 1000000){
      val message = s"Size grater than ${1000000} or less than 0"
      throw new IllegalArgumentException(message)
    }

    val ids = new Array[Long](size)
    for(i <- 0 until size){
      ids(i) = nextId
    }

    logger.info("nextId result = {}", ids)

    ids
  }

  def nextId(): Long = {

    var timestamp = timeGen

    if(lastTimestamp == timestamp){
      sequence = sequence + 1 & sequenceMask

      if(sequence == 0){
        timestamp = tilNextMillis(lastTimestamp)
      }
    }else{
      sequence = 0
    }

    if(timestamp < lastTimestamp){
      throw new RuntimeException("clock moved backwards Exception")
    }

    lastTimestamp = timestamp

    timestamp - epoch << timestampLeftShift | workerId << workerIdShift | sequence

  }

  def tilNextMillis(l: Long): Long = {
    var timestamp = timeGen()
    while(timestamp <= lastTimestamp){
      timestamp = timeGen()
    }

    timestamp
  }

  def timeGen(): Long = {
    System.currentTimeMillis()
  }

}

object Snowflake{
  def create(workerId: Long): Snowflake = {
    new Snowflake(workerId)
  }
}
