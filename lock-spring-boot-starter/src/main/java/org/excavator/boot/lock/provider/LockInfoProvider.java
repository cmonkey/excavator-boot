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
package org.excavator.boot.lock.provider;

import org.aspectj.lang.reflect.MethodSignature;
import org.excavator.boot.lock.annotation.Lock;
import org.excavator.boot.lock.config.LockConfig;
import org.excavator.boot.lock.enums.LockType;
import org.excavator.boot.lock.model.LockInfo;
import org.excavator.boot.lock.provider.BusinessKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.aspectj.lang.ProceedingJoinPoint;

public class LockInfoProvider {

    private static final Logger logger              = LoggerFactory
                                                        .getLogger(LockInfoProvider.class);

    public static final String  LOCK_NAME_PREFIX    = "lock";
    public static final String  LOCK_NAME_SEPARATOR = ".";

    @Autowired
    private LockConfig lockConfig;

    @Autowired
    private BusinessKeyProvider businessKeyProvider;

    public LockInfo get(ProceedingJoinPoint joinPoint, Lock lock) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        LockType type = lock.lockType();

        String businessKeyName = businessKeyProvider.getKeyName(joinPoint, lock);

        String lockName = LOCK_NAME_PREFIX + LOCK_NAME_SEPARATOR + getName(lock.name(), signature)
                          + businessKeyName;

        long waitTime = getWaitTime(lock);

        long leaseTime = getLeaseTime(lock);

        logger
            .info(
                "lockInfo Provider info in type = {}, keyName = {}, lockName = {}, waitTime = {}, leaseTime = {}",
                type, businessKeyName, lockName, waitTime, leaseTime);

        return new LockInfo(type, lockName, waitTime, leaseTime);
    }

    private String getName(String annotationName, MethodSignature signature) {
        if (annotationName.isEmpty()) {
            return String.format("%s.%s", signature.getDeclaringTypeName(), signature.getMethod()
                .getName());
        } else {
            return annotationName;
        }
    }

    private long getWaitTime(Lock lock) {
        return lock.waitTime() == Long.MIN_VALUE ? lockConfig.getWaitTime() : lock.waitTime();
    }

    private long getLeaseTime(Lock lock) {
        return lock.leaseTime() == Long.MIN_VALUE ? lockConfig.getLeaseTime() : lock.leaseTime();
    }
}
