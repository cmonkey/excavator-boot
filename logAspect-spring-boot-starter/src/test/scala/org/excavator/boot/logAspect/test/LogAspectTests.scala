package org.excavator.boot.logAspect.test

import javax.annotation.Resource
import org.junit.Test
import org.junit.Assert._
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[LogAspectApplication]))
class LogAspectTests {

  @Resource
  val logAspectController: LogAspectController = null

  @Test
  def testAspectLog() = {
    assertNotNull(logAspectController.aspectLog())
  }

}
