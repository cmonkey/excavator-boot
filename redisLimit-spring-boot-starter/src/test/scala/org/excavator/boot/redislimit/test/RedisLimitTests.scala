package org.excavator.boot.redislimit.test

import javax.annotation.Resource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest
class RedisLimitTests {
  @Resource
  val testRedisLimitService: TestRedisLimitService = null

  @Test
  def testLimit() = {
    val r = testRedisLimitService.limit()

    assertEquals(r, "success")
  }
}
