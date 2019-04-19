package org.excavator.boot.reactor.netty

import reactor.core.publisher.Mono
import reactor.netty.tcp.TcpClient

class TcpClient {

  def connect(host:String, port: Int) = {
    val connection = TcpClient.create()
      .host(host)
      .port(port)
      .handle((inbound, outbound) => outbound.sendString(Mono.just("Hello")))
      .connectNow()

    connection.onDispose().block()
  }
}