package org.excavator.boot.redislimit.test

import javax.annotation.Resource
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.junit.Assert._
@RunWith(classOf[SpringRunner])
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
