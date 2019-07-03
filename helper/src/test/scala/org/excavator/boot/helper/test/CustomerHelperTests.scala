package org.excavator.boot.helper.test

import java.util.concurrent.atomic.AtomicReference

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

  @Test
  @DisplayName("test CreateToken")
  @Order(0)
  def testCreateToken(): Unit = {
    customerHelper.createToken(customerId, expireSeconds) match {
      case Some(value) => {
        assertNotNull(value)
        CustomerHelperTests.atomicReference.set(value)
      }
      case None => logger.warn("create Token is failed")
    }
  }

  @Test
  @DisplayName("test getCustomerId")
  @Order(1)
  def testGetCustomerId(): Unit = {
    val token = CustomerHelperTests.atomicReference.get()
    logger.info(s"token = ${token}")
    customerHelper.getCustomerId(token) match {
      case Some(value) => {
        assertNotNull(value)
        assertTrue(value == customerId)
      }
      case None => logger.warn(s"token ${token} get customerId is null")
    }
  }

}

object CustomerHelperTests{
  val atomicReference = new AtomicReference[String]()
}
