package org.excavator.boot.netty.component

import java.util.UUID

import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import org.slf4j.LoggerFactory

@Sharable
class LengthFieldRpcHandler extends SimpleChannelInboundHandler[String] {

  val logger = LoggerFactory.getLogger(classOf[LengthFieldRpcHandler])

  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    val remoteAddress = ctx.channel.remoteAddress.toString
    val localAddress = ctx.channel.localAddress.toString

    val globalId = UUID.randomUUID.toString.replaceAll("-", "")

    var result = msg

    if(logger.isDebugEnabled()) {
      logger.debug(s"remoteAddress = $remoteAddress, localAddress = ${localAddress}, globalId = ${globalId}")
      logger.debug(s"Channel send msg  = ${result}")
    }

    val length = result.getBytes.length

    result = "%08d".format(length) + result

    ctx.pipeline.writeAndFlush(result)
  }
}
