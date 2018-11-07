package com.excavator.boot.netty.client

import java.nio.charset.Charset
import java.util.concurrent.locks.ReentrantLock

import com.excavator.boot.netty.component.{RpcDecoder, RpcHandler}
import com.excavator.boot.netty.enums.ResponseViewMode
import com.google.common.base.{Charsets, StandardSystemProperty}
import io.netty.bootstrap.Bootstrap
import io.netty.channel.epoll.{EpollEventLoopGroup, EpollSocketChannel}
import io.netty.channel._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringEncoder
import org.slf4j.LoggerFactory

class NettyClient(host:String, port:Int, charset: Charset, responseViewMode: ResponseViewMode){
  private val logger = LoggerFactory.getLogger(classOf[NettyClient])

  val lock = new ReentrantLock()

  def this(host:String, port:Int) = {
    this(host, port, Charsets.UTF_8, ResponseViewMode.FULL)
  }

  def this(host:String, port:Int, charset: Charset) = {
    this(host, port, charset, ResponseViewMode.FULL)
  }

  var bootstrap: Bootstrap = null
  var channelFuture: ChannelFuture = null

  init()

  private def init(): Unit = {
    lock.lock()

    bootstrap = new Bootstrap()

    var workerGroup:EventLoopGroup = null

    if (StandardSystemProperty.OS_NAME.value == "Linux") {
      workerGroup = new EpollEventLoopGroup
      bootstrap.channel(classOf[EpollSocketChannel])
    }
    else {
      workerGroup = new NioEventLoopGroup
      bootstrap.channel(classOf[NioSocketChannel])
    }
    bootstrap.group(workerGroup)
    bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
    bootstrap.handler(new ChannelInitializer[SocketChannel]() {
      override protected def initChannel(socketChannel: SocketChannel): Unit = {
        socketChannel.pipeline
          .addLast(new RpcDecoder(1024 * 1024 * 10, 8, charset, responseViewMode))
          .addLast(new StringEncoder(charset))
          .addLast(new RpcHandler)
      }
    })

    channelFuture = bootstrap.connect(host, port).sync()

    if(logger.isDebugEnabled()){
      logger.debug(s"init bootstrap = ${bootstrap}")
    }

    lock.unlock()
  }

}
