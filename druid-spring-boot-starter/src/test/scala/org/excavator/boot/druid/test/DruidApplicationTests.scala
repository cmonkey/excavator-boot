package org.excavator.boot.druid.test

import java.util.concurrent.TimeUnit

import javax.annotation.Resource
import org.junit.Test
import org.junit.Assert._
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[DruidApplication]))
class DruidApplicationTests {

  @Resource
  val druidService: DruidService = null

  @Test
  def testQuery() = {

    val id = 0L
    val rId = druidService.getId()
    assertNotEquals(id, rId)

  }
}
