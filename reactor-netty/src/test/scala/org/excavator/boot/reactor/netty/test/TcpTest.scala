package org.excavator.boot.reactor.netty.test

import org.excavator.boot.reactor.netty.{ReactorTcpClient, ReactorTcpServer}
import org.junit.jupiter.api.Test

class TcpTest {

  val host = "localhost"
  val port = 8080

  @Test
  def testTcp() = {
    val tcpServer = new ReactorTcpServer()
    tcpServer.start(host, port)
    val tcpClient = new ReactorTcpClient()
    tcpClient.connect(host, port)
  }
}
