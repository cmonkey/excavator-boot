package org.excavator.boot.redislimit.test

import org.excavator.boot.redislimit.annotation.RedisLimiter
import org.springframework.stereotype.Service

@Service
class TestRedisLimitService {

  @RedisLimiter(expire = 20, limit = 5)
  def limit() = {
    "success"
  }
}
