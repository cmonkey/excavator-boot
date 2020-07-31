package org.excavator.boot.experiment.test

import java.util.Properties

import io.lettuce.core.{RedisClient, RedisURI}
import org.apache.commons.lang3.StringUtils
import org.excavator.boot.experiment.TryWithResources
import org.junit.jupiter.api.{BeforeAll, DisplayName, Test}
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

import scala.util.Using

class TryWithResourcesTest {

  val log = LoggerFactory.getLogger(classOf[TryWithResourcesTest])

  @Test
  @DisplayName("testWithResources")
  def testWithResources() = {
    val redisClient = TryWithResourcesTest.redisClient
    TryWithResources.apply().withResources(redisClient.connect())(connection => {
      val reactiveRedisCommands = connection.reactive()
      Flux.just("foo", "bar").flatMap(key => {
        reactiveRedisCommands.get(key)
      }).subscribe(t => {
        log.info("t = [{}]", t)
      })
    })

  }

}

object TryWithResourcesTest{

  var redisClient: RedisClient = _

  @BeforeAll
  def init() = {
    Using(getClass.getClassLoader.getResourceAsStream("application.properties")){inputStream => {
      val properties = new Properties()
      properties.load(inputStream)

      val host = properties.getProperty("spring.redis.host", "127.0.0.1")
      val port = properties.getProperty("spring.redis.port", "6379").toInt
      val auth = properties.getProperty("spring.redis.password", "")
      val database = properties.getProperty("spring.redis.database", "1").toInt

      val builder = RedisURI.builder.withHost(host).withPort(port).withDatabase(database)

      if(StringUtils.isNotBlank(auth)){
        builder.withPassword(auth)
      }

      redisClient = RedisClient.create(builder.build())
    }}
  }

}

