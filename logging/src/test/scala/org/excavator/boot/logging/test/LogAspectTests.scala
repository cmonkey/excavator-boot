package org.excavator.boot.logAspect.test

import javax.annotation.Resource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[LogAspectApplication]))
class LogAspectTests {

  @Resource
  val logAspectController: LogAspectController = null

  @Test
  def testAspectLog() = {
    assertNotNull(logAspectController.aspectLog())
  }

}
