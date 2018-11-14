package org.excavator.boot.kafka.test

import java.util.concurrent.TimeUnit

import javax.annotation.Resource
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert._
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
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
