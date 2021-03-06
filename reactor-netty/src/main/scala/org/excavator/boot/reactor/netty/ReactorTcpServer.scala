package org.excavator.boot.reactor.netty

import java.io.File
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.ChannelOption
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.timeout.ReadTimeoutHandler
import reactor.core.publisher.{Flux, Mono}
import reactor.netty.resources.LoopResources
import reactor.netty.tcp.TcpServer

class ReactorTcpServer{

  val DEFAULT_PORT: Int = 8080

  def createServerBootstrap(): ServerBootstrap = {

    new ServerBootstrap().option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
      .option[java.lang.Boolean](ChannelOption.SO_REUSEADDR, true)
      .option[java.lang.Integer](ChannelOption.SO_BACKLOG, 1000)
      .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
      .childOption[java.lang.Integer](ChannelOption.SO_RCVBUF, 1024 * 1024)
      .childOption[java.lang.Integer](ChannelOption.SO_SNDBUF, 1024 * 1024)
      .childOption[java.lang.Boolean](ChannelOption.AUTO_READ, false)
      .childOption[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true)
      .childOption[java.lang.Boolean](ChannelOption.TCP_NODELAY, true)
      .childOption[java.lang.Integer](ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
      .localAddress(new InetSocketAddress(DEFAULT_PORT))
  }


  def start(host:String , port: Int) = {

    val loopResources = LoopResources.create("event-loop", 1, 4, true)

    val server = TcpServer.create()
      .host(host)
      .port(port)
      .option[java.lang.Integer](ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
      .wiretap(true)
      .runOn(loopResources)
      //.secure(spec => {
        //SslContextBuilder.forServer(new File("cert.crt"), new File("cert.key"))
      //})
      .doOnConnection(conn => {
        conn.addHandler(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
      })
      .handle((inbound, outbound) => {
        inbound.receive().then()
        //outbound.options(o => o.flushOnEach(false))
        outbound.sendString(Flux.just("Hello", "World", "!"))
      })
      .bindNow()

    server.onDispose().block()
  }
}
