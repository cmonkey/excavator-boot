package org.excavator.boot.idworker.storage

import java.util.concurrent.{TimeUnit, TimeoutException}

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.locks.InterProcessMutex
import org.excavator.boot.common.helper.JSONProxy
import org.excavator.boot.idworker.registry.RegisterCenter
import org.slf4j.LoggerFactory

import scala.util.control.Breaks._

class SnowflakeNodeRegister(val registerCenter: RegisterCenter, val nodePath: SnowflakeNodePath) {
  val logger = LoggerFactory.getLogger(classOf[SnowflakeNodeRegister])

  val MAX_WORKER_NUM = 1024
  val MAX_LOCK_WAIT_TIME_MS = 30 * 1000

  def register(): Long = {

    var registerWorkerId = -1L

    val curatorFramework = registerCenter.getRawClient().asInstanceOf[CuratorFramework]

    val lock = new InterProcessMutex(curatorFramework, nodePath.getGroupPath)

    try {

      val numOfChildren = registerCenter.getChildrenNum(nodePath.getWorkerPath())

      if (numOfChildren < MAX_WORKER_NUM) {
        if (!lock.acquire(MAX_LOCK_WAIT_TIME_MS, TimeUnit.MILLISECONDS)) {
          throw new TimeoutException("acquire lock failed after")
        }

        val children = registerCenter.getChildrenKeys(nodePath.getWorkerPath())

        breakable(
          for (workerId <- 0 until MAX_WORKER_NUM) {

            val workerIdStr = String.valueOf(workerId)

            if (!children.contains(workerIdStr)) {

              val key = nodePath.getWorkerPath() + "/" + workerId

              val snowflakeNodeInfo = new SnowflakeNodeInfo(workerId)

              val value = JSONProxy.toJSONString(snowflakeNodeInfo.getFieldMap())

              registerCenter.persistEphemeral(key, value)

              registerWorkerId = workerId

              break
            }
          }
        )
      } else {
        throw new IllegalArgumentException("children num exception")
      }
    }catch {
      case ex:Exception => logger.error("Exception = {}", ex)
    }finally {
      try{
        lock.release()
      }catch {
        case e: Exception => logger.error("lock release Exception = {}", e)
      }
    }

    registerWorkerId
  }

}
