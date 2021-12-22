package org.excavator.boot.experiment

import java.util.Random

object ProducerConsumerExample {

  private val random = new Random()

  def main(args: Array[String]): Unit = {

    val buffer = new Buffer()
    val producer = new Thread(() => {
      while (true) {
        buffer.addItem(getRandomItem())
      }
    })

    producer.setName("Producer # 1")

    val producer2 = new Thread(() => {
      while (true) {
        buffer.addItem(getRandomItem())
      }
    })

    producer2.setName("Producer # 2")

    val consumer1 = new Thread(() => {
      while (true) {
        buffer.getItem()
      }
    })

    consumer1.setName("Consumer # 1")

    val consumer2 = new Thread(() => {
      while (true) {
        buffer.getItem()
      }
    })

    consumer2.setName("Consumer # 2")

    producer.start()
    producer2.start()

    consumer1.start()
    consumer2.start()
  }

  private def getRandomItem() = {
    random.nextInt()
  }

}
