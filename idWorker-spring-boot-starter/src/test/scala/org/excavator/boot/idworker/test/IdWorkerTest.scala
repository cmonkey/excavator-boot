package org.excavator.boot.idworker.test

import javax.annotation.Resource
import org.excavator.boot.idworker.generator.IdGenerator
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.junit.Assert._

@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[IdWorkerApplication]))
class IdWorkerTest {

  @Resource
  val idGenerator: IdGenerator = null

  @Test
  def next() = {
    val nextId = idGenerator.nextId
    assertEquals(nextId.toString.length,18)
  }
}
