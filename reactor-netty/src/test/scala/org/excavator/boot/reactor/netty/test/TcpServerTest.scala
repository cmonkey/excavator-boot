package org.excavator.boot.reactor.netty.test

import org.excavator.boot.reactor.netty.TcpServer
import org.junit.jupiter.api.Test

class TcpServerTest {

  val host = "localhost"
  val port = 8080

  @Test
  def testTcpServerStart() = {
    val tcpServer = new TcpServer()
    tcpServer.start(host, port)
  }
}
