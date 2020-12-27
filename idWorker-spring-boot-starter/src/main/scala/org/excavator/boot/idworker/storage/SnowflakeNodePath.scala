package org.excavator.boot.idworker.storage

import org.apache.commons.lang3.StringUtils

class SnowflakeNodePath(val namespace:String, val groupName: String, val workerId: Long) {
  val WORKER_NODE = "worker"

  def this(groupName: String) = {
    this("idWorker", groupName, 0)
  }

  def this(workerId: Long) = {
    this("idWorker", "default", workerId)
  }

  def this(namespace: String, groupName:String) = {
    this(namespace, groupName, 0)
  }

  def getGroupPath() = {
    appendNamespace(s"/${groupName}")
  }

  def getWorkerPath() = {
    appendNamespace(s"/${groupName}/${WORKER_NODE}")
  }

  def getWorkerIdPath() = {
    appendNamespace(s"/${groupName}/${WORKER_NODE}/${workerId}")
  }

  def appendNamespace(path:String): String ={
    if(StringUtils.isEmpty(namespace)){
      path
    }else{
      s"/${namespace}${path}"
    }
  }

}
