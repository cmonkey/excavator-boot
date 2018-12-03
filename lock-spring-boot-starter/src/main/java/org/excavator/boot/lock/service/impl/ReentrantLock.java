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
package org.excavator.boot.lock.service.impl;

import org.excavator.boot.lock.model.LockInfo;
import org.excavator.boot.lock.service.LockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class ReentrantLock implements LockService {

    private RLock          rLock;

    private LockInfo       lockInfo;

    private RedissonClient redissonClient;

    public ReentrantLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean acquire() {
        try {
            rLock = redissonClient.getLock(lockInfo.getName());
            return rLock.tryLock(lockInfo.getWaitTime(), lockInfo.getLeaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void release() {
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlockAsync();
        }

    }

    public LockInfo getLockInfo() {
        return lockInfo;
    }

    @Override
    public LockService setLockInfo(LockInfo lockInfo) {
        this.lockInfo = lockInfo;
        return this;
    }
}
