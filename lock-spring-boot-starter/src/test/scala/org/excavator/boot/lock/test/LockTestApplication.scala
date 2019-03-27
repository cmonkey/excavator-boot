package org.excavator.boot.lock.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LockTestApplication {

}

object LockTestApplication{
  def main(args: String*): Unit ={
    SpringApplication.run(classOf[LockTestApplication], args:_*)
  }
}
