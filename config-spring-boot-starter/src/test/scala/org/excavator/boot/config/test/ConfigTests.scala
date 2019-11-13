package org.excavator.boot.config.test

import javax.annotation.Resource
import org.assertj.core.api.Assertions
import org.excavator.boot.config.test.controller.ConfigController
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions._
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[ConfigApplication]),webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigTests {

  @LocalServerPort
  val port: Int = 0

  @Resource
  val restTemplate: TestRestTemplate = null

  @Resource
  val configController: ConfigController = null

  @Test
  @DisplayName("check configController is not null ")
  def testConfigControllerIsNotNull(): Unit = {
    Assertions.assertThat(configController).isNotNull
  }

  @Test
  @DisplayName("test resource home")
  def testHome(): Unit = {
    val r = restTemplate.getForObject("http://localhost:" + port + "/v1/", classOf[String])
    Assertions.assertThat(r).contains("Hello")
  }
}
