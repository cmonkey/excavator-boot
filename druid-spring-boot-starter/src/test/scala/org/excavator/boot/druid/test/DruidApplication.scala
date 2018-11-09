package org.excavator.boot.druid.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

class DruidApplication {

  def main(args: String*) = {
    SpringApplication.run(classOf[DruidApplication], args:_*)
  }
}

@SpringBootApplication
//@ComponentScan(basePackages = Array("org.excavator.boot.druid"))
object DruidApplication{

}
