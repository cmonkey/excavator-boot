package org.excavator.boot.idworker.storage

import java.lang.management.ManagementFactory
import java.net.{InetAddress, UnknownHostException}
import java.util

import org.excavator.boot.common.helper.JSONProxy

class SnowflakeNodeInfo(workerId: Int, var ip:String = "", var hostName: String = "", var pid: String = "") {
  try{
    ip = InetAddress.getLocalHost.getHostAddress
    hostName = InetAddress.getLocalHost.getHostName
    pid = ManagementFactory.getRuntimeMXBean.getName.split("@").head
  }catch{
    case ex: UnknownHostException => print("exception ")
  }

  override def toString: String = {
    val map = new util.HashMap[String, String]()

    map.put("ip" , ip)
    map.put("hostName" , hostName)
    map.put("pid" , pid)
    map.put("workerId" , workerId.toString)


    map.toString
  }

}
