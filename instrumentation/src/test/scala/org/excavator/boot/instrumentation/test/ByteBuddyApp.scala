package org.excavator.boot.instrumentation.test

import java.util.concurrent.TimeUnit

import org.excavator.boot.instrumentation.application.MyAtm

object ByteBuddyApp extends App{

  MyAtm.withdrawMoney(10)
  TimeUnit.SECONDS.sleep(10)

}
