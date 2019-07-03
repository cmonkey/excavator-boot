package org.excavator.boot.helper.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = Array("org.excavator.boot.helper"))
class HelperApplication {

}

object HelperApplication{
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[HelperApplication], args:_*)
  }
}
