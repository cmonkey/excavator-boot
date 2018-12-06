package org.excavator.boot.quartz.config

import java.sql.SQLException

import com.alibaba.druid.pool.DruidDataSource
import org.excavator.boot.quartz.properties.QuartzProperties
import com.mchange.v2.c3p0.ComboPooledDataSource
import javax.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class QuartzDataSourceConfig {

  val logger = LoggerFactory.getLogger(classOf[QuartzDataSourceConfig])

  @Resource
  val quartzProperties: QuartzProperties = null

  @Bean(name = Array("quartzDataSource"))
  def quartzDataSource() = {
    val dataSource = initDataSource(quartzProperties)

    logger.info("init quartzDataSource")

    dataSource
  }

  def getComboPooledDataSource(quartzProperties: QuartzProperties): ComboPooledDataSource = {
    val dataSource = new ComboPooledDataSource()

    dataSource.setJdbcUrl(quartzProperties.getUrl)
    dataSource.setUser(quartzProperties.getUsername)
    dataSource.setPassword(quartzProperties.getPassword)
    dataSource.setDriverClass(quartzProperties.getDriverClassName)
    dataSource.setInitialPoolSize(5)
    dataSource.setMaxPoolSize(30)
    dataSource.setMinPoolSize(5)
    dataSource.setAcquireIncrement(30)
    dataSource.setMaxIdleTime(1800)
    dataSource.setTestConnectionOnCheckin(true)
    dataSource.setTestConnectionOnCheckout(true)
    dataSource.setIdleConnectionTestPeriod(18000)
    dataSource.setPreferredTestQuery("SELECT 1")

    dataSource
  }

  private def initDataSource(properties: QuartzProperties): DruidDataSource = {
    val druidDataSource = new DruidDataSource
    druidDataSource.setDriverClassName(properties.getDriverClassName)
    druidDataSource.setUrl(properties.getUrl)
    druidDataSource.setUsername(properties.getUsername)
    druidDataSource.setPassword(properties.getPassword)
    druidDataSource.setInitialSize(properties.getInitialSize)
    druidDataSource.setMinIdle(properties.getMinIdle)
    druidDataSource.setMaxActive(properties.getMaxActive)
    druidDataSource.setMaxWait(properties.getMaxWait)
    druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis)
    druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis)
    druidDataSource.setValidationQuery(properties.getValidationQuery)
    druidDataSource.setTestWhileIdle(properties.getTestWhileIdle)
    druidDataSource.setTestOnBorrow(properties.getTestOnBorrow)
    druidDataSource.setTestOnReturn(properties.getTestOnReturn)
    druidDataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements)
    druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize)
    druidDataSource.setConnectionProperties(properties.getConnectionProperties)
    try {
      druidDataSource.setFilters(properties.getFilters)
      druidDataSource.init()
    } catch {
      case e: SQLException =>
        e.printStackTrace()
    }

    return druidDataSource
  }
}
