package org.excavator.boot.druid.autoconfigure

import com.alibaba.druid.pool.DruidDataSource
import javax.sql.DataSource
import org.excavator.boot.druid.properties.DruidMonitorProperties
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.{ConditionalOnClass, ConditionalOnProperty}
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean

@Configuration
@ConditionalOnClass(Array(classOf[DruidDataSource]))
@EnableConfigurationProperties(Array(classOf[DruidMonitorProperties]))
@ConditionalOnProperty(name = Array("excavator.druid.monitor.enabled"), havingValue = "true")
@AutoConfigureAfter(Array(classOf[DataSource]))
class DruidMonitorAutoConfiguration{

  private val logger = LoggerFactory.getLogger(classOf[DruidMonitorAutoConfiguration])

  @Bean
  @ConditionalOnMissingBean
  def druidServlet(properties: DruidMonitorProperties): ServletRegistrationBean[StatViewServlet] = {
    val servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet, properties.getStatView)
    //添加初始化参数：initParams
    //白名单：
    servletRegistrationBean.addInitParameter("allow", properties.getAllow)
    //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
    servletRegistrationBean.addInitParameter("deny", properties.getDeny)
    //登录查看信息的账号密码.
    servletRegistrationBean.addInitParameter("loginUsername", properties.getLoginName)
    servletRegistrationBean.addInitParameter("loginPassword", properties.getLoginPassword)
    //是否能够重置数据.
    servletRegistrationBean.addInitParameter("resetEnable", properties.getResetEnable)

    logger.info(s"init druidServlet in servletRegistrationBean = $servletRegistrationBean")

    servletRegistrationBean
  }

  @Bean
  @ConditionalOnMissingBean
  def filterRegistrationBean(properties: DruidMonitorProperties): FilterRegistrationBean[WebStatFilter] = {
    val filterRegistrationBean = new FilterRegistrationBean[WebStatFilter]

    filterRegistrationBean.setFilter(new WebStatFilter)
    filterRegistrationBean.addUrlPatterns(properties.getUrlPatterns)
    filterRegistrationBean.addInitParameter("exclusions", properties.getExclusions)

    logger.info(s"init filterRegistrationBean = ${filterRegistrationBean}")

    filterRegistrationBean
  }

}

