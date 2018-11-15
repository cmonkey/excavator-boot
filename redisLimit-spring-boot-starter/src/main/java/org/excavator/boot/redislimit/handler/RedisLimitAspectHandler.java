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
package org.excavator.boot.redislimit.handler;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.excavator.boot.redislimit.annotation.RedisLimiter;
import org.excavator.boot.redislimit.service.RedisLimitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @author cmonkey
 */
@Aspect
@Component
public class RedisLimitAspectHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisLimitAspectHandler.class);

    @Resource(name = "redisLimitTemplate")
    RedisTemplate               redisTemplate;

    @Resource(name = "redisLimitScript")
    DefaultRedisScript<Long>    defaultRedisScript;

    @Resource(shareable = false)
    RedisLimitService           redisLimitService;

    @Around(value = "@annotation(redisLimiter)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLimiter redisLimiter) throws Throwable {
        long startTime = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();

        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("the annotation @RedisLimiter must used on Method");
        }

        String methodName = getMethod(joinPoint).getName();

        String key = redisLimiter.key();

        if (StringUtils.isBlank(key)) {
            key = getName(key, (MethodSignature) signature);
        }

        long limitTime = redisLimiter.limit();
        long expireTime = redisLimiter.expire();

        logger.info("redis limiter method = {}, key = {}, limit = {}, expire = {}", methodName,
            key, limitTime, expireTime);

        List<String> keys = Collections.singletonList(key);

        long result = (long) redisTemplate.execute(defaultRedisScript, keys, limitTime, expireTime);

        logger.info("redis limiter time = {}", System.currentTimeMillis() - startTime);

        if (result == 0) {
            logger.warn("redis limiter result is zero");
            return redisLimitService.limit();
        } else {
            return joinPoint.proceed();
        }
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass()
                    .getDeclaredMethod(signature.getName(), method.getParameterTypes());
            } catch (Exception e) {
                logger.error("getMethod Exception = {}", e);
            }
        }
        return method;
    }

    private String getName(String annotationName, MethodSignature signature) {
        if (annotationName.isEmpty()) {
            return String.format("%s:%s", signature.getDeclaringTypeName(), signature.getMethod()
                .getName());
        } else {
            return annotationName;
        }
    }
}