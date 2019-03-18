package org.excavator.boot.reactor.netty.test

import org.excavator.boot.reactor.netty.{TcpClient, TcpServer}
import org.junit.jupiter.api.Test

class TcpTest {

  val host = "localhost"
  val port = 8080

  @Test
  def testTcp() = {
    val tcpServer = new TcpServer()
    tcpServer.start(host, port)
    val tcpClient = new TcpClient()
    tcpClient.connect(host, port)
  }
}
