package org.excavator.boot.helper.test

import javax.annotation.Resource
import org.excavator.boot.helper.CustomerHelper
import org.junit.jupiter.api.{DisplayName, Order, Test}
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions._
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[HelperApplication]))
class CustomerHelperTests {

  val logger = LoggerFactory.getLogger(classOf[CustomerHelperTests])

  @Resource
  val customerHelper: CustomerHelper = null

  val expireSeconds = 600

  val customerId = 1
  var token = ""

  @Test
  @DisplayName("test CreateToken")
  @Order(1)
  def testCreateToken(): Unit = {
    customerHelper.createToken(1, expireSeconds) match {
      case Some(value) => {
        assertNotNull(value)
        token = value
      }
      case None => logger.warn("create Token is failed")
    }
  }
}
