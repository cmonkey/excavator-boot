package org.excavator.boot.processor.test

import org.junit.Test
import org.junit.Assert._
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[ProcessorApplication]))
class ProcessorTests {

  @Value("${author}")
  var author: String = null

  @Test
  def testAspectLog() = {
    assertNotNull(author)
  }

}
