package org.excavator.boot.authorization.test

import java.util.concurrent.atomic.AtomicReference

import javax.annotation.Resource
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.model.Token
import org.junit.jupiter.api.{DisplayName, Order, Test, TestMethodOrder}
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod}
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[AuthorizationApplication]), webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(classOf[OrderAnnotation])
class AuthorizationTests {

  @Resource
  val testRestTemplate: TestRestTemplate = null

  @Resource
  val tokenManager: TokenManager = null

  val customerId = 1


  @Test
  @DisplayName("testToken in controller")
  @Order(1)
  def testToken() = {
    val token = testRestTemplate.getForObject("/token", classOf[String])

    assertNotNull(token)

    val headers = setHeaders("Authorization", token)

    val httpEntiry = new HttpEntity[String]("", headers)

    val auth = testRestTemplate.exchange("/auth", HttpMethod.GET, httpEntiry, classOf[Boolean])

    assertEquals(200, auth.getStatusCodeValue)
    assertEquals(true,auth.getBody)

    val msg = testRestTemplate.exchange("/msg", HttpMethod.GET, httpEntiry, classOf[String])

    assertEquals(200, msg.getStatusCodeValue)

    assertNotNull(msg.getBody)

  }

  def setHeaders(header:String, value: String) = {
    val httpHeaders = new HttpHeaders()
    httpHeaders.add(header,value)
    httpHeaders
  }

  @Test
  @DisplayName("test CreateToken")
  @Order(2)
  def testCreateToken(): Unit = {
    val option = tokenManager.createToken(customerId)
    assertTrue(option.isPresent)

    option.ifPresent(token => {
      assertNotNull(token)
      AuthorizationTests.atomicReference.set(token)
    })
  }

  @Test
  @DisplayName("test CheckToken")
  @Order(3)
  def testCheckToken(): Unit = {
    val checkStatus = tokenManager.checkToken(AuthorizationTests.atomicReference.get())

    assertTrue(checkStatus)
  }

  @Test
  @DisplayName("test GetToken")
  @Order(4)
  def testGetToken(): Unit = {
    val optional = tokenManager.getToken(AuthorizationTests.atomicReference.get().getToken)

    assertTrue(optional.isPresent)

    optional.ifPresent(token => {
      assertTrue(token.getToken == AuthorizationTests.atomicReference.get().getToken)
      assertTrue(token.getCustomerId == AuthorizationTests.atomicReference.get().getCustomerId)
    })
  }

  @Test
  @DisplayName("test DelToken")
  @Order(5)
  def testDelToken(): Unit = {
    val token = AuthorizationTests.atomicReference.get().getToken
    tokenManager.deleteToken(token)

    val optional = tokenManager.getToken(token)

    assertNotEquals(true, optional.isPresent)
  }
}

object AuthorizationTests{
  val atomicReference = new AtomicReference[Token]()
}
