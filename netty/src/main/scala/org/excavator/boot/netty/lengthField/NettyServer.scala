package org.excavator.boot.netty.lengthField

import org.excavator.boot.netty.component.{RpcDecoder, RpcHandler}
import org.excavator.boot.netty.enums.ResponseViewMode
import com.google.common.base.{Charsets, StandardSystemProperty}
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelInboundHandler, ChannelInitializer, ChannelOption, EventLoopGroup}
import io.netty.channel.epoll.{EpollEventLoopGroup, EpollServerSocketChannel}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.logging.{LogLevel, LoggingHandler}
import org.slf4j.LoggerFactory

class NettyServer(maxFrameLength: Int, position: Int, port: Int, channelInBoundHandler: ChannelInboundHandler) {

  val logger = LoggerFactory.getLogger(classOf[NettyServer])

  private var bossGroup: EventLoopGroup = null
  private var workerGroup: EventLoopGroup = null

  def this(channelInboundHandler: ChannelInboundHandler) = {
    this(10 * 1024 * 1024, 8, 9500, channelInboundHandler)
  }

  def this(port:Int, channelInboundHandler: ChannelInboundHandler) = {
    this(10 * 1024 * 1024, 8, port, channelInboundHandler)
  }

  private def groups(bootstrap: ServerBootstrap) = {
    if (StandardSystemProperty.OS_NAME.value == "Linux") {
      bossGroup = new EpollEventLoopGroup(1)
      workerGroup = new EpollEventLoopGroup
      bootstrap.channel(classOf[EpollServerSocketChannel]).group(bossGroup, workerGroup)
    }
    else {
      bossGroup = new NioEventLoopGroup(1)
      workerGroup = new NioEventLoopGroup
      bootstrap.channel(classOf[NioServerSocketChannel]).group(bossGroup, workerGroup)
    }

    bootstrap.option[java.lang.Boolean](ChannelOption.SO_REUSEADDR, true)
      .option[java.lang.Integer](ChannelOption.SO_BACKLOG, 512)
      .childOption[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true)
      .childOption[java.lang.Integer](ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)

    if(logger.isDebugEnabled()) {
      logger.debug(s"Bootstrap lengthField config = ${bootstrap}")
    }
  }

  def run(args:String*) = {
    val bootstrap = new ServerBootstrap()

    groups(bootstrap)

    bootstrap.childHandler(new ChannelInitializer[SocketChannel]() {
      override protected def initChannel(socketChannel: SocketChannel): Unit = {
        socketChannel.pipeline
          .addLast(new LoggingHandler(LogLevel.INFO))
          .addLast(new RpcDecoder(maxFrameLength, position, Charsets.UTF_8, ResponseViewMode.BODY))
          .addLast(new StringEncoder(Charsets.UTF_8))
          .addLast(channelInBoundHandler)
      }
    })

    val future = bootstrap.bind(port).sync
    val cause = future.cause

    if (null != cause) {
      throw new RuntimeException("netty lengthField startup Exception = {}", cause)
    }

    if(logger.isDebugEnabled()){
      logger.info(s"Netty lengthField RPC started on port(s) = ${port}, headerLength = ${position}")
    }
  }

  def shutdown(): Unit = {
    try {

      if (null != bossGroup){
        bossGroup.shutdownGracefully.await
      }

      if (null != workerGroup){
        workerGroup.shutdownGracefully.await
      }

      logger.info(s"Stopped Netty lengthField Rpc Server port on = ${port}")
    } catch {
      case e: InterruptedException =>
        logger.error(s"shutdown Netty lengthField Rpc Server Exception = ${e}")
    }
  }
}
