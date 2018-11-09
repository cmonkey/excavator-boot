package org.excavator.boot.kafka.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KafkaApplication {

}

object KafkaApplication{
  def main(args:String*) = {
    SpringApplication.run(classOf[KafkaApplication], args:_*)
  }
}
