package org.excavator.boot.authorization.test

import javax.annotation.Resource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions._
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod}
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[AuthorizationApplication]), webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthorizationTests {

  @Resource
  val testRestTemplate: TestRestTemplate = null

  @Test
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

}
