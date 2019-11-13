package org.excavator.boot.config.test.service

import java.util.concurrent.ArrayBlockingQueue

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ConfigService {
  val logger = LoggerFactory.getLogger(classOf[ConfigService])

  val blockingQueue = new ArrayBlockingQueue[String](1)

}
