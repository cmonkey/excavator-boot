package org.excavator.boot.cumulative.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CumulativeApplication {

}

object CumulativeApplication{
  def main(args:String*) = {
    SpringApplication.run(classOf[CumulativeApplication],args:_*)
  }
}
