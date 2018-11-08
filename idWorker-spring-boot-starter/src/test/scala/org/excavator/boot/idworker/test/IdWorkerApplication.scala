package org.excavator.boot.idworker.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class IdWorkerApplication {

}

object IdWorkerApplication{
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[IdWorkerApplication])
  }
}
