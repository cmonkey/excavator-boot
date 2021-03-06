package org.excavator.boot.lock.test

import org.excavator.boot.lock.annotation.{Lock, LockKey}
import org.springframework.aop.framework.AopContext
import org.springframework.stereotype.Service

@Service
class TestLockService {
  @Lock(waitTime = 1, leaseTime = 1)
  def getValue(param: String) = "success"

  @Lock(keys = Array("#userId"))
  @throws[Exception]
  def getValue(userId: String, @LockKey id: Int): String = {
    Thread.sleep(60 * 1000)
    "success"
  }

  @Lock
  def getByValue(param: String, @LockKey value: String) = {
    param + value
  }

  @Lock(waitTime = 1, leaseTime = 1)
  def getAopService(name:String) = {
    AopContext.currentProxy().asInstanceOf[TestLockService].getByValue(name, "value")
  }
}
