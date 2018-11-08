package org.excavator.boot.idworker.storage

import java.lang.management.ManagementFactory
import java.net.{InetAddress, UnknownHostException}

class SnowflakeNodeInfo(workerId: Int) {

  var ip: String = null
  var hostName: String = null
  var pid: String = null

  try{
    ip = InetAddress.getLocalHost.getHostAddress
    hostName = InetAddress.getLocalHost.getHostName
    pid = ManagementFactory.getRuntimeMXBean.getName.split("@").head
  }catch{
    case ex: UnknownHostException => print("exception ")
  }

}
