package org.excavator.boot.schedule.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ScheduleApplication {

}

object ScheduleApplication{
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[ScheduleApplication], args:_*)
  }
}
