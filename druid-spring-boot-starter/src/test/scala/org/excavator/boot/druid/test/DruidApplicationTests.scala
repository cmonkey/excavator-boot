package org.excavator.boot.druid.test

import com.alibaba.druid.support.http.WebStatFilter
import javax.annotation.Resource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[DruidApplication]))
class DruidApplicationTests {

  @Resource
  val druidService: DruidService = null

  @Resource
  val filterRegistrationBean: FilterRegistrationBean[WebStatFilter] = null

  @Test
  def testQuery() = {

    val id = 0L
    val rId = druidService.getId()
    assertNotEquals(id, rId)

  }

  @Test
  def testFilterRegistrationBean() = {
    assertNotNull(filterRegistrationBean)
  }
}
