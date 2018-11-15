package org.excavator.boot.redislimit.service;

public interface RedisLimitService {

    default Object limit() { return "limit";}
}
