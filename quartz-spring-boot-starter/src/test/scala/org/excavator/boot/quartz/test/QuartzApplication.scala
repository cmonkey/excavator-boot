package org.excavator.boot.quartz.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class QuartzApplication {

}

object QuartzApplication{
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[QuartzApplication], args:_*)
  }
}
