package org.excavator.boot.netty.component

import org.excavator.boot.netty.response.ResponseFuture
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}

class RpcHandler extends SimpleChannelInboundHandler[String]{
  var responseFuture: ResponseFuture = null

  def setResponseFuture(responseFuture: ResponseFuture) = {
    this.responseFuture = responseFuture
  }


  override def channelRead0(channelHandlerContext: ChannelHandlerContext, request: String): Unit = {

    if(null != request){
      responseFuture.set(request)
    }
  }
}
