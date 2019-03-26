package org.excavator.boot.kafka.test

import javax.annotation.Resource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[KafkaApplication]))
class KafkaTests {

  @Resource
  val kafkaService: KafkaService = null

  val msg = "foo testing"

  @Test
  def testForSendMsg() = {
    val topic = "foo"
    val r = kafkaService.sendMsg(topic, msg)

    assertNotNull(r)
  }

  @Test
  def testPollMsg(): Unit = {
    val r = kafkaService.takeMsg()

    assertEquals(msg, r)
  }
}
