package org.excavator.boot.idworker.algorithm

import org.joda.time.{DateTime, format}
import org.joda.time.format.DateTimeFormat
import java.security.SecureRandom
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

  val RANDOM = new SecureRandom()

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
      ids(i) = nextId()
    }

    logger.info("nextId result = {}", ids)

    ids
  }

  def nextId(): Long = {

    var timestamp = timeGen()

    //发生了时间回拨，　当前时间小于上次发号时间
    if(timestamp < lastTimestamp){
      val offset = lastTimestamp - timestamp

      if(offset <= 5){
        try {
          // 时间爱你偏差大小小于5ms, 等待两倍时间
          wait(offset << 1)
          timestamp = timeGen()
          if (timestamp < lastTimestamp) {
            // 抛出时间回拨异常
            throw new RuntimeException("clock moved backwards Exception")
          }
        }catch {
          case _:Throwable => throw new RuntimeException("wait interrupts Exception")
        }
      }
    }

    if(lastTimestamp == timestamp){
      sequence = sequence + 1 & sequenceMask

      if(sequence == 0){
        /**
          *
          * sequence 为0 的时候表示是下一个毫秒时间，　开始对sequence 做随机
          * 数据量大时，一般做分库分表，id 作为一个取模的依据, 为了分库分表均匀
          * id 生成需要“取模随机", sequence 在id最末位，保证id随机
          * 如果跨毫秒，每次都将sequence 归0
          * 会导致sequence 为0 的序号比较多, 导致生成的id取模后不均匀
          * ref:github.com:Meituan-Dianping/Leaf.git
          * ref:https://mp.weixin.qq.com/s/0H-GEXlFnM1z-THI8ZGV2Q
          **/
        sequence = RANDOM.nextInt(10)
        timestamp = tilNextMillis(lastTimestamp)
      }
    }else{
      sequence = RANDOM.nextInt(10)
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
