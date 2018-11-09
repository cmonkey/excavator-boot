package org.excavator.boot.druid.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DruidApplication {
}

object DruidApplication{

  def main(args: String*) = {
    SpringApplication.run(classOf[DruidApplication], args:_*)
  }
}
