package org.excavator.boot.authorization.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AuthorizationApplication {

}

object AuthorizationApplication{
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[AuthorizationApplication], args:_*)
  }
}
