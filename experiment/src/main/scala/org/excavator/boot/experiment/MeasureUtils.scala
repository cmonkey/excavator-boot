package org.excavator.boot.experiment

import java.lang.management.ManagementFactory

object MeasureUtils {

  case class MeasurementResult[R](result:R, executionTime:Long, memoryConsumed:Long)

  def measure[R](block: => R): MeasurementResult[R] = {
    val startTime = System.nanoTime()
    val memoryMXBean = ManagementFactory.getMemoryMXBean
    val heapMemoryUsage = memoryMXBean.getHeapMemoryUsage
    val usedHeapMemoryBefore = heapMemoryUsage.getUsed

    val result = block

    val endTime = System.nanoTime()
    val executionTime = endTime - startTime

    val heapMemoryUsageAfter = memoryMXBean.getHeapMemoryUsage.getUsed
    val memoryConsumed= heapMemoryUsageAfter - usedHeapMemoryBefore

    MeasurementResult(result,executionTime,memoryConsumed)
  }
  def measureTime[R](block: => R): (R,Long) = {
    val startTime = System.nanoTime()
    val result = block
    val endTime = System.nanoTime()
    val executionTime = endTime - startTime
    (result, executionTime)
  }

  def measureMemory[R](block: => R): (R,Long) = {
    val memoryMXBean = ManagementFactory.getMemoryMXBean
    val heapMemoryUsage = memoryMXBean.getHeapMemoryUsage
    val usedHeapMemoryBefore = heapMemoryUsage.getUsed
    val result = block
    val heapMemoryUsageAfter = memoryMXBean.getHeapMemoryUsage.getUsed
    val memoryConsumed = heapMemoryUsageAfter - usedHeapMemoryBefore
    (result, memoryConsumed)
  }
}
