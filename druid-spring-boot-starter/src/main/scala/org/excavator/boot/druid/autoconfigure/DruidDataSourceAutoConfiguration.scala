package org.excavator.boot.druid.autoconfigure

import java.sql.SQLException

import com.alibaba.druid.pool.DruidDataSource
import org.excavator.boot.druid.properties.DruidDataSourceProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.{ConditionalOnClass, ConditionalOnMissingBean}
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration, Primary}
;

@Configuration
@ConditionalOnClass(Array(classOf[DruidDataSource]))
@EnableConfigurationProperties(Array(classOf[DruidDataSourceProperties]))
@AutoConfigureBefore(Array(classOf[DataSourceAutoConfiguration]))
class DruidDataSourceAutoConfiguration{
  private val logger = LoggerFactory.getLogger(classOf[DruidDataSourceAutoConfiguration])

  @Bean(destroyMethod = "close")
  @ConditionalOnMissingBean
  @Primary
  def dataSource(properties: DruidDataSourceProperties) = {
    val druidDataSource = new DruidDataSource()

      druidDataSource.setDriverClassName(properties.getDriverClassName())
    druidDataSource.setUrl(properties.getUrl())
    druidDataSource.setUsername(properties.getUsername())
    druidDataSource.setPassword(properties.getPassword())
    druidDataSource.setInitialSize(properties.getInitialSize())
    druidDataSource.setMinIdle(properties.getMinIdle())
    druidDataSource.setMaxActive(properties.getMaxActive())
    druidDataSource.setMaxWait(properties.getMaxWait())
    druidDataSource.setTimeBetweenEvictionRunsMillis(properties
          .getTimeBetweenEvictionRunsMillis())
    druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis())
    druidDataSource.setValidationQuery(properties.getValidationQuery())
    druidDataSource.setTestWhileIdle(properties.getTestWhileIdle())
    druidDataSource.setTestOnBorrow(properties.getTestOnBorrow())
    druidDataSource.setTestOnReturn(properties.getTestOnReturn())
    druidDataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements())
    druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties
          .getMaxPoolPreparedStatementPerConnectionSize())
    druidDataSource.setConnectionProperties(properties.getConnectionProperties())

    try {
          druidDataSource.setFilters(properties.getFilters())
          druidDataSource.init()
    } catch {
        case e:SQLException => e.printStackTrace()
      }

      logger.info(s"init druidDataSource in properties $properties")

      druidDataSource

  }
}
