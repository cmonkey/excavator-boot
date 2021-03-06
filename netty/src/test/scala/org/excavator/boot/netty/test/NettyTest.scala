package org.excavator.boot.netty.test

import java.nio.charset.StandardCharsets
import java.util.UUID
import java.util.concurrent.TimeUnit

import org.apache.commons.io.{Charsets, IOUtils}
import org.excavator.boot.netty.client.NettyClient
import org.excavator.boot.netty.handler.LengthFieldRpcHandler
import org.excavator.boot.netty.enums.ResponseViewMode
import org.excavator.boot.netty.lengthField.NettyServer
import org.junit.jupiter.api._
import org.junit.jupiter.api.Assertions._
import org.slf4j.LoggerFactory

class NettyTest {
  private val logger = LoggerFactory.getLogger(classOf[NettyTest])

  var requestId = ""

  var data = ""

  var responseViewMode: ResponseViewMode = ResponseViewMode.BODY

  @BeforeEach
  def initRequestId() ={
    requestId = UUID.randomUUID().toString.replaceAll("-", "").substring(0, 8)
    data = requestId
  }

  @DisplayName("testMinBody")
  @RepeatedTest(10)
  def testMinBody() = {
    data += "abcdefghijklmnopqrstuvwxyz"

    testBody()
  }

  @DisplayName("testInBody")
  @RepeatedTest(10) 
  def testInBody(): Unit = {
    for (i <- 0 until 1004){
      data += "a"
    }

    testBody()
  }

  @DisplayName("testMaxBody")
  @RepeatedTest(10) 
  def testMaxBody(): Unit = {
    for(i <- 0 until 1000){
      data += "abcdefghijklmnopqrstuvwxyz"
    }
    testBody()
  }

  @DisplayName("test1013")
  @RepeatedTest(10)
  def test1013() = {
    for(i <-0 until 1013-8){
      data += "a"
    }

    testBody()
  }

  @DisplayName("test90040")
  @RepeatedTest(2)
  def test90040() = {
    for(i <-0 until 90040){
      data += "A"
    }

    testBody()
  }

  @DisplayName("testTimeout")
  @RepeatedTest(10)
  def testTimeout() = {
    for(i <- 0 until 1000){
      data += "timeout"
    }

    testBody(true)
  }

  @DisplayName("test1017")
  @RepeatedTest(10)
  def test1017() = {
    for(i <- 0 until 1017-8){
      data += "a"
    }

    testBody(true)
  }

  @Test
  @DisplayName("testNettyBlockingOperationException")
  def testNettyBlockingOperationException(): Unit = {
    val classLoader = classOf[NettyTest].getClassLoader
    val stream = classLoader.getResourceAsStream("nettyBlockingOperationException.txt")
    val lines = IOUtils.readLines(stream, StandardCharsets.UTF_8)

    data += lines

    testBody()
  }

  def testBody(isTimeout: Boolean = false) = {
    val length = data.getBytes.length

    data = "%08d".format(length) + data

    val nettyClient = new NettyClient("localhost", 9500)

    val result = if (isTimeout) nettyClient.send(data, 1, TimeUnit.SECONDS) else nettyClient.send(data)

    logger.info(s"result = $result")

    assertNotNull(result)

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

object NettyTest{
  var nettyServer: NettyServer = _

  @BeforeAll
  def initNettyServer() = {
    nettyServer = new NettyServer(9500, new LengthFieldRpcHandler)
    nettyServer.run("")
  }

  @AfterAll
  def shutdown() = {
    nettyServer.shutdown()
  }
}
