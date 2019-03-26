package org.excavator.boot.idworker.test

import javax.annotation.Resource
import org.excavator.boot.idworker.generator.IdGenerator
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
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
