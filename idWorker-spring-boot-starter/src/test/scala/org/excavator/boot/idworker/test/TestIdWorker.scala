package org.excavator.boot.idworker.test

import javax.annotation.Resource
import org.excavator.boot.idworker.generator.IdGenerator
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[IdWorkerApplication]))
class TestIdWorker {

  @Resource
  val idGenerator: IdGenerator = null

  @Test
  def next() = {
    val nextId = idGenerator.nextId
    assert(nextId.toString.length == 18)
  }
}
