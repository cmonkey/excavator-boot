package org.excavator.boot.reactor.netty

import reactor.core.publisher.{Flux, Mono}
import reactor.netty.tcp.TcpClient

class ReactorTcpClient {

  def connect(host:String, port: Int) = {
    val connection = TcpClient.create()
      .host(host)
      .port(port)
      .handle((inbound, outbound) => outbound.sendString(Mono.just("Hello")))
      .connectNow()

    connection.onDispose().block()
  }
}
