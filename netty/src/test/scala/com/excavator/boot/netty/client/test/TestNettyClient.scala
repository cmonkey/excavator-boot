package com.excavator.boot.netty.client.test

import java.util.UUID

import com.excavator.boot.netty.client.NettyClient
import com.excavator.boot.netty.enums.ResponseViewMode
import org.junit.jupiter.api._
import org.junit.jupiter.api.Assertions._
import org.slf4j.LoggerFactory

class TestNettyClient {
  private val logger = LoggerFactory.getLogger(classOf[TestNettyClient])

  var requestId = ""

  var data = ""

  var responseViewMode: ResponseViewMode = ResponseViewMode.BODY

  @BeforeEach
  def initRequestId() ={
    requestId = UUID.randomUUID().toString.replaceAll("-", "").substring(0, 8)
    data = requestId
  }

  @Test
  @DisplayName("testMinBody")
  @RepeatedTest(100)
  def testMinBody() = {
    data += "abcdefghijklmnopqrstuvwxyz"

    testBody()
  }

  def testBody() = {
    val length = data.getBytes.length

    data = "%08d".format(length) + data

    val nettyClient = new NettyClient("localhost", 9500)

    val result = nettyClient.send(data)

    logger.info(s"result = ${result}")

    assertNotNull(result)

    import com.excavator.boot.netty.enums.ResponseViewMode
    var responseId = result.substring(0, 8)

    if (responseViewMode eq ResponseViewMode.FULL) {
      responseId = result.substring(8, 16)
      assertEquals(data, result)
    }

    assertEquals(requestId, responseId)
  }

  @AfterEach
  def clear() = {
    data = ""
    requestId = ""
  }
}
