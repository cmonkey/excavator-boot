package org.excavator.boot.netty.component

import java.nio.charset.Charset

import org.excavator.boot.netty.enums.ResponseViewMode
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import org.slf4j.LoggerFactory

class RpcDecoder(maxFrameLength: Int, position: Int, charset: Charset, responseViewMode: ResponseViewMode)
  extends LengthFieldBasedFrameDecoder(maxFrameLength, 0, position, 0, position){
  val logger = LoggerFactory.getLogger(classOf[RpcDecoder])

  if(logger.isDebugEnabled()){
    logger.debug(s"rpcDecoder init maxFrameLength = $maxFrameLength, position = $position, charset = $charset, responseViewMode = $responseViewMode")
  }

  override def decode(ctx: ChannelHandlerContext, in: ByteBuf): AnyRef = {

    if(in.readableBytes() < position){
      return null
    }

    val beginIndex = in.readerIndex()

    val headLengthByte = Array.fill[Byte](position)(0)

    in.readBytes(headLengthByte)

    val headLength = new String(headLengthByte, charset)

    val length = Integer.parseInt(headLength)

    if(logger.isDebugEnabled){
      logger.debug(s"decode headLength = $headLength to IntLength = $length")
    }

    if((in.readableBytes() + 1 ) < length){
      in.readerIndex(beginIndex)
      return null
    }

    in.readerIndex(beginIndex + position + length)

    val dataByte = in.slice(position, length)
    dataByte.retain()

    val bodyByte = Array.fill[Byte](length)(0)
    dataByte.readBytes(bodyByte)

    var msg = new String(bodyByte, charset)

    if(responseViewMode == ResponseViewMode.FULL){
      msg = headLength + msg

      if(logger.isDebugEnabled()){
        logger.debug(s"decode responseViewMode = $responseViewMode, fullMsg = $msg")
      }
    }else{
      if(logger.isDebugEnabled()){
        logger.debug(s"decode responseViewMode = $responseViewMode, bodyMsg = $msg")
      }
    }

    msg
  }

}
