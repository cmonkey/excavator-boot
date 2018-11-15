package org.excavator.boot.redislimit.factory;

import org.excavator.boot.redislimit.service.RedisLimitService;

public interface RedisLimitServiceFactory {

    RedisLimitService getService(String name);
}
