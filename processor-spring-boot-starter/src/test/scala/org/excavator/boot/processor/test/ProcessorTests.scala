package org.excavator.boot.processor.test

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[ProcessorApplication]))
class ProcessorTests {

  @Value("${author}")
  var author: String = _

  @Test
  def testAspectLog() = {
    assertNotNull(author)
  }

}
