package org.excavator.boot.config.test

import java.nio.file.{Files, Paths}
import java.util
import java.util.stream.Collectors

import com.google.common.collect.{Lists, Maps}
import javax.annotation.Resource
import org.assertj.core.api.Assertions
import org.excavator.boot.config.test.controller.ConfigController
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions._
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.LinkedMultiValueMap

import scala.collection.mutable.ArrayBuffer

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
  @DisplayName("test findAll")
  def testFindAll(): Unit = {
    val r = restTemplate.getForObject("http://localhost:" + port + "/v1/users", classOf[java.util.List[String]])
    println(s"test findAll r = ${r}")
    Assertions.assertThat[String](r).contains("foo")
  }

  @Test
  @DisplayName("test getUserName")
  def testGetUserName(): Unit = {
    val userName = "cmonkey"
    val params = Maps.newHashMap[String, String]()

    params.put("userName", userName)

    val r = restTemplate.getForObject("http://localhost:" + port + "/v1/users/{userName}", classOf[String], params)

    println(s"getUserName = ${r}")

    Assertions.assertThat(r).contains(userName)
  }

  @Test
  @DisplayName("testAddUserName")
  def testAddUserName(): Unit = {
    val userName = "42"
    val params = Maps.newHashMap[String, String]()
    params.put("userName", userName)

    val r = restTemplate.postForObject("http://localhost:"+port+"v1/users?userName={userName}", null, classOf[Boolean], params)
    println(s"addUserName = ${r}")

    Assertions.assertThat(r).isTrue
  }

  @Test
  @DisplayName("testAddUserNameByBody")
  def testAddUserNameByBody: Unit = {
    val userName = "42"

    val params = new util.HashMap[String, String]()
    params.put("userName", userName)

    val r = restTemplate.postForObject("http://localhost:"+port+"v1/users/body", params, classOf[Boolean])

    println(s"addUserNameByBody = ${r}")

    Assertions.assertThat(r).isTrue
  }

  @Test
  @DisplayName("testUpload")
  def testUpload(): Unit = {
    val file = System.getProperty("user.dir")
    Files.list(Paths.get(file)).findFirst().ifPresent(p => {
      val resource = new FileSystemResource(p.toUri.getPath)

      val params = new LinkedMultiValueMap[String, Any]()

      params.add("userName", "42")
      params.add("files", resource)

      val r = restTemplate.postForObject("http://localhost:"+port+"/v1/upload", params, classOf[Boolean])

      println(s"upload status = ${r}")

      Assertions.assertThat(r).isTrue
    })
  }

  @Test
  @DisplayName("testUploads")
  def testUploads(): Unit = {
    val file = System.getProperty("user.dir")

    val params = new LinkedMultiValueMap[String, Any]()
    params.add("userName", "42")

    Files.list(Paths.get(file)).filter(path => !Files.isDirectory(path)).forEach(path => {
      val resource = new FileSystemResource(path.toUri.getPath)
      params.add("files", resource)
    })

    val r = restTemplate.postForObject("http://localhost:"+port+"/v1/upload", params, classOf[Boolean])

    println(s"uploads status = ${r}")

    Assertions.assertThat(r).isTrue
  }
}
