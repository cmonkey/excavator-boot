package org.excavator.boot.kafka.test

import java.util.concurrent.ArrayBlockingQueue

import javax.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class KafkaService {
  val logger = LoggerFactory.getLogger(classOf[KafkaService])

  val blockingQueue = new ArrayBlockingQueue[String](1)

  @Resource
  val kafkaTemplate: KafkaTemplate[String, String] = null

  def sendMsg(topic:String, msg: String) = {
    logger.info(s"sendMsg in topic = ${topic} msg = ${msg}")

    kafkaTemplate.send(topic, msg)
  }

  @KafkaListener(topics = Array("foo"))
  def topicListener(@Payload msg:String) = {
    logger.info(s"topicListener poll msg = ${msg}")
    blockingQueue.put(msg)
  }

  def takeMsg() = {
    blockingQueue.take()
  }
}
