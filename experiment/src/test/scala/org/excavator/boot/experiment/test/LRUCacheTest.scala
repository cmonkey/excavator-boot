package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.LRUCache
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.{DisplayName, Test}

class LRUCacheTest {

  @Test
  @DisplayName("test lru cache")
  def testLRUCache(): Unit = {
    val key = 1
    val lruCache = new LRUCache(10)
    lruCache.put(key, 10)
    val value = lruCache.get(key)
    assertEquals(value, 10)
  }

}
