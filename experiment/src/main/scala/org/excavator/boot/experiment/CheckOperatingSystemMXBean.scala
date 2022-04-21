package org.excavator.boot.experiment

import com.sun.management.OperatingSystemMXBean

import java.lang.management.ManagementFactory

object CheckOperatingSystemMXBean extends App{

  println("Checking OperatingSystemMXBean")

  val osBean = ManagementFactory.getOperatingSystemMXBean.asInstanceOf[OperatingSystemMXBean]

  println("Runtime.availableProcessors: %d".format(Runtime.getRuntime.availableProcessors()))
  println("OperatingSystemMXBean.getAvailableProcessors: %d".format(osBean.getAvailableProcessors))
  println("OperatingSystemMXBean.getTotalPhysicalMemorySize: %d".format(osBean.getTotalMemorySize))
  println("OperatingSystemMXBean.getFreePhysicalMemorySize: %d".format(osBean.getFreeMemorySize))
  println("OperatingSystemMXBean.getTotalSwapSpaceSize: %d".format(osBean.getTotalSwapSpaceSize))
  println("OperatingSystemMXBean.getFreeSwapSpaceSize: %d".format(osBean.getFreeSwapSpaceSize))
  println("OperatingSystemMXBean.getSystemCpuLoad: %f".format(osBean.getCpuLoad))

}
