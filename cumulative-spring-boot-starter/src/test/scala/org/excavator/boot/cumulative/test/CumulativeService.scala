package org.excavator.boot.cumulative.test

import javax.annotation.Resource
import org.excavator.boot.cumulative.service.Cumulative
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

@Service
class CumulativeService {
  private val logger = LoggerFactory.getLogger(classOf[CumulativeService])

  @Resource
  private val cumulative:Cumulative = null

  def cumulativeByDay(key: String, dimensionKey: String, value: String): String = {
    try
      cumulative.countByDay(key, new Cumulative.Dimension(dimensionKey, value))
    catch {
      case e: IllegalAccessException =>
        logger.error("cumulative Exception = {}", e)
    }
    cumulative.queryByDay(key, dimensionKey)
  }

  def cumulativeByMonth(key: String, dimensionKey: String, value: String): String = {
    try
      cumulative.countByMonth(key, new Cumulative.Dimension(dimensionKey, value))
    catch {
      case e: IllegalAccessException =>
        logger.error("cumulative Exception = {}", e)
    }
    cumulative.queryByMonth(key, dimensionKey)
  }

  def cumulativeByYear(key: String, dimensionKey: String, value: String): String = {
    try
      cumulative.countByYear(key, new Cumulative.Dimension(dimensionKey, value))
    catch {
      case e: IllegalAccessException =>
        logger.error("cumulative Exception = {}", e)
    }
    cumulative.queryByYear(key, dimensionKey)
  }

  def cumulativeByDayAndMonth(key: String, dimensionKey: String, value: String): Unit = {
    try
      cumulative.countByDayAndMonth(key, new Cumulative.Dimension(dimensionKey, value))
    catch {
      case e: IllegalAccessException =>
        logger.error("cumulative Exception = {}", e)
    }
  }

  def queryByDay(key: String, dimensionKey: String): String = cumulative.queryByDay(key, dimensionKey)

  def queryByMonth(key: String, dimensionKey: String): String = cumulative.queryByMonth(key, dimensionKey)

  def queryByYear(key: String, dimensionKey: String): String = cumulative.queryByYear(key, dimensionKey)
}
