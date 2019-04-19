package org.excavator.boot.logAspect.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LogAspectApplication {

}

object LogAspectApplication{
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[LogAspectApplication], args:_*)
  }

}
