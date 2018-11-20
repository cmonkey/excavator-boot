package org.excavator.boot.logAspect.test

import org.excavator.boot.common.helper.RemoteIpHelper
import org.excavator.boot.common.utils.IPThreadLocal
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/"))
class LogAspectController {
  val logger = LoggerFactory.getLogger(classOf[LogAspectController])

  def aspectLog() = {
    logger.info(s"aspectLog method running in time = ${System.nanoTime()}")
    IPThreadLocal.getRemoteIp
  }

}
