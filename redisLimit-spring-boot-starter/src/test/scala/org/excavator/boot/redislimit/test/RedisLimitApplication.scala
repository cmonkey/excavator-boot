package org.excavator.boot.redislimit.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RedisLimitApplication {

}

object RedisLimitApplication{

  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[RedisLimitApplication], args:_*)
  }

}
