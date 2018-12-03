/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.lock.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.excavator.boot.lock.annotation.Lock;
import org.excavator.boot.lock.enums.LockType;
import org.excavator.boot.lock.model.LockInfo;
import org.excavator.boot.lock.provider.LockInfoProvider;
import org.excavator.boot.lock.service.impl.FairLock;
import org.excavator.boot.lock.service.impl.ReadLock;
import org.excavator.boot.lock.service.impl.ReentrantLock;
import org.excavator.boot.lock.service.impl.WriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class LockFactory {
    private static final Logger                     logger  = LoggerFactory
                                                                .getLogger(LockFactory.class);

    @Autowired
    private RedissonClient                          redissonClient;

    @Autowired
    private LockInfoProvider lockInfoProvider;

    private static final Map<LockType, LockService> lockMap = new HashMap<>();

    @PostConstruct
    public void init() {
        lockMap.put(LockType.Reentrant, new ReentrantLock(redissonClient));
        lockMap.put(LockType.Fair, new FairLock(redissonClient));
        lockMap.put(LockType.Read, new ReadLock(redissonClient));
        lockMap.put(LockType.Write, new WriteLock(redissonClient));
        logger.info("Lock Initialization Successful");
    }

    public LockService getLock(ProceedingJoinPoint joinPoint, Lock lock) {
        LockInfo lockInfo = lockInfoProvider.get(joinPoint, lock);
        return lockMap.get(lockInfo.getType()).setLockInfo(lockInfo);
    }

}
