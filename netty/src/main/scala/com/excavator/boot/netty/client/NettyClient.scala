package com.excavator.boot.netty.client

import java.nio.charset.Charset

import com.google.common.base.Charsets

class NettyClient(host:String, port:Int, charset: Charset){
  def this(host:String, port:Int) = {
    this(host, port, Charsets.UTF_8)
  }

}
