package org.excavator.boot.netty.test

import java.util

import org.junit.jupiter.api.{DisplayName, Test}
import org.slf4j.LoggerFactory

class BloomFilterTest {

  private val logger = LoggerFactory.getLogger(classOf[BloomFilterTest])

  @Test
  @DisplayName("testMapTest")
  def hashMapTest() = {
    val startTime = System.currentTimeMillis()

    val num = 10000000
    val set = new util.HashSet[Int](num)

    for(i <-0 until num){
      set.add(i)
    }

    assert(set.contains(1))
    assert(set.contains(2))
    assert(set.contains(3))

    logger.info(s"execute time = ${System.currentTimeMillis() - startTime}")
  }
}
