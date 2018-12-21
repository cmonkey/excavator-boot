package org.excavator.boot.netty.client

import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

import org.excavator.boot.netty.component.{RpcDecoder, RpcHandler}
import org.excavator.boot.netty.enums.ResponseViewMode
import org.excavator.boot.netty.response.ResponseFuture
import com.google.common.base.{Charsets, StandardSystemProperty}
import io.netty.bootstrap.Bootstrap
import io.netty.channel.epoll.{EpollEventLoopGroup, EpollSocketChannel}
import io.netty.channel._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.concurrent.GenericFutureListener
import org.slf4j.LoggerFactory

class NettyClient(host:String, port:Int,maxFrameLength:Int, position: Int, charset: Charset, responseViewMode: ResponseViewMode){
  private val logger = LoggerFactory.getLogger(classOf[NettyClient])

  private val lock = new ReentrantLock()
  private var bootstrap: Bootstrap = null
  private var workerGroup:EventLoopGroup = null
  private var channelFuture: ChannelFuture = null

  def this(host:String, port:Int) = {
    this(host, port, 10 * 1024 * 1024, 8, Charsets.UTF_8, ResponseViewMode.BODY)
  }

  def this(host:String, port:Int, charset: Charset) = {
    this(host, port, 10 * 1024 * 1024, 8, charset, ResponseViewMode.BODY)
  }

  def this(host: String, port:Int, maxFrameLength: Int, position: Int){
    this(host, port, maxFrameLength, position, Charsets.UTF_8, ResponseViewMode.BODY)
  }

  init()

  private def init(): Unit = {
    lock.lock()

    bootstrap = new Bootstrap()

    if (StandardSystemProperty.OS_NAME.value == "Linux") {
      workerGroup = new EpollEventLoopGroup
      bootstrap.channel(classOf[EpollSocketChannel])
    }
    else {
      workerGroup = new NioEventLoopGroup
      bootstrap.channel(classOf[NioSocketChannel])
    }
    bootstrap.group(workerGroup)
    bootstrap.option[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true)
    bootstrap.handler(new ChannelInitializer[SocketChannel]() {
      override protected def initChannel(socketChannel: SocketChannel): Unit = {
        socketChannel.pipeline
          .addLast(new RpcDecoder(maxFrameLength, position, charset, responseViewMode))
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

  def send(msg:String): String = send(msg, 0, TimeUnit.SECONDS, false)

  def send(msg: String, timeout:Long, timeUnit: TimeUnit) = send(msg, timeout, timeUnit, true)

  private def send(msg:String, timeout:Long, timeUnit: TimeUnit, isTimeout: Boolean): String = {
    lock.lock()
    try{

      val responseFuture = new ResponseFuture()

      channelFuture.addListener(new GenericFutureListener[ChannelFuture](){
        override def operationComplete(f: ChannelFuture): Unit = {
          channelFuture.channel.pipeline.get(classOf[RpcHandler]).setResponseFuture(responseFuture)
          channelFuture.channel.writeAndFlush(msg).sync
        }
      })

      if(isTimeout){
        responseFuture.get(timeout, timeUnit)
      }else{
        responseFuture.get()
      }

    }finally {
      channelFuture.channel().close()
      workerGroup.shutdownGracefully()
      lock.unlock()
    }
  }
}
