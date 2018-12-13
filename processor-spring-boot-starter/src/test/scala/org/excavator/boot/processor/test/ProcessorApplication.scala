package org.excavator.boot.processor.test

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ProcessorApplication {

}

object ProcessorApplication{
  val logger = LoggerFactory.getLogger(classOf[ProcessorApplication])

  def main(args: Array[String]): Unit = {
    val content = SpringApplication.run(classOf[ProcessorApplication], args:_*)

    val author = content.getEnvironment.getProperty("author")

    logger.info(s"pre properties author = ${author}")

    content.close()
  }

}
