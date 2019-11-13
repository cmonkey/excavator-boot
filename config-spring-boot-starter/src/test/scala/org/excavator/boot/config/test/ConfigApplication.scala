package org.excavator.boot.config.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ConfigApplication {

}

object ConfigApplication{
  def main(args:String*) = {
    SpringApplication.run(classOf[ConfigApplication], args:_*)
  }
}
