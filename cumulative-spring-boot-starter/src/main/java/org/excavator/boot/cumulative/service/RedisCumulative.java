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
package org.excavator.boot.cumulative.service;

import org.excavator.boot.cumulative.serializer.LongRedisSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedisCumulative implements Cumulative {
    private static final Logger                 logger          = LoggerFactory
                                                                    .getLogger(RedisCumulative.class);

    public static final String                  HASH_KEY_PREFIX = "CUMULATIVE.";

    private final RedisTemplate<String, String> redisTemplate;
    private final DefaultRedisScript<Long>      defaultRedisScript;

    public RedisCumulative(RedisTemplate<String, String> redisTemplate,
                           DefaultRedisScript<Long> defaultRedisScript) {
        this.redisTemplate = redisTemplate;
        this.defaultRedisScript = defaultRedisScript;
    }

    @Override
    public void countByDay(String key, Dimension... dimensions) throws IllegalAccessException {
        if (StringUtils.isBlank(key) || null == dimensions || dimensions.length <= 0) {
            throw new IllegalAccessException("param key or dimensions is null");
        }
        List<String> keys = new ArrayList<>();

        keys.add(getKey(HASH_KEY_PREFIX, key, CumulativeMode.DAY));
        keys.add("");

        List<String> args = new ArrayList<>();

        args.add(CumulativeMode.DAY.getMode());
        args.add("");

        for (Dimension d : dimensions) {
            keys.add(d.getKey());
            args.add(d.getValue());
        }

        Object[] argsArray = args.toArray(new String[] {});
        if (logger.isDebugEnabled()) {
            logger.debug("redis day cumulative with keys {} ~~~ args {}", keys, argsArray);
        }
        Long rst = this.redisTemplate.execute(defaultRedisScript, new StringRedisSerializer(),
            new LongRedisSerializer(), keys, argsArray);

        if (rst != 0) {
            throw new RuntimeException("redis day cumulative occured error ");
        }
    }

    @Override
    public void countByMonth(String key, Dimension... dimensions) throws IllegalAccessException {
        if (StringUtils.isBlank(key) || null == dimensions || dimensions.length <= 0) {
            throw new IllegalAccessException("param key or dimensions is null.");
        }
        List<String> keys = new ArrayList<>();

        keys.add(getKey(HASH_KEY_PREFIX, key, CumulativeMode.MONTH));
        keys.add("");

        List<String> args = new ArrayList<>();
        args.add(CumulativeMode.MONTH.getMode());
        args.add("");

        for (Dimension d : dimensions) {
            keys.add(d.getKey());
            args.add(d.getValue());
        }

        Object[] argsArray = args.toArray(new String[] {});

        if (logger.isDebugEnabled()) {
            logger.debug("redis month cumulative with keys {} ~~~ args {}", keys, argsArray);
        }
        Long rst = this.redisTemplate.execute(defaultRedisScript, new StringRedisSerializer(),
            new LongRedisSerializer(), keys, argsArray);

        if (rst != 0) {
            throw new RuntimeException("exception");
        }
    }

    @Override
    public void countByDayAndMonth(String key, Dimension... dimensions)
                                                                       throws IllegalAccessException {
        if (StringUtils.isBlank(key) || null == dimensions || dimensions.length <= 0) {
            throw new IllegalAccessException("param key or dimensions is null.");
        }

        List<String> keys = new ArrayList<>();

        keys.add(getKey(HASH_KEY_PREFIX, key, CumulativeMode.DAY));
        keys.add(getKey(HASH_KEY_PREFIX, key, CumulativeMode.MONTH));

        List<String> args = new ArrayList<>();
        args.add(CumulativeMode.DAY_AND_MONTH.getMode());
        args.add("");

        for (Dimension d : dimensions) {
            keys.add(d.getKey());
            args.add(d.getValue());
        }

        Object[] argsArray = args.toArray(new String[] {});
        if (logger.isDebugEnabled()) {
            logger
                .debug("redis day and month cumulative with keys {} ~~~ args {}", keys, argsArray);
        }

        Long rst = this.redisTemplate.execute(defaultRedisScript, new StringRedisSerializer(),
            new LongRedisSerializer(), keys, argsArray);
        if (rst != 0) {
            throw new RuntimeException("redis day and month cumulative occured error, keys " + keys
                                       + ", args " + Arrays.toString(argsArray));
        }
    }

    @Override
    public void countByYear(String key, Dimension... dimensions) throws IllegalAccessException {
        if (StringUtils.isBlank(key) || null == dimensions || dimensions.length <= 0) {
            throw new IllegalAccessException("param key or dimensions is null.");
        }
        List<String> keys = new ArrayList<>();

        keys.add(getKey(HASH_KEY_PREFIX, key, CumulativeMode.YEAR));
        keys.add("");

        List<String> args = new ArrayList<>();

        args.add(CumulativeMode.YEAR.getMode());
        args.add("");

        for (Dimension d : dimensions) {
            keys.add(d.getKey());
            args.add(d.getValue());
        }

        Object[] argsArray = args.toArray(new String[] {});
        if (logger.isDebugEnabled()) {
            logger.debug("redis year cumulative with keys {} ~~~ args {}", keys, argsArray);
        }
        Long rst = this.redisTemplate.execute(defaultRedisScript, new StringRedisSerializer(),
            new LongRedisSerializer(), keys, argsArray);

        if (rst != 0) {
            throw new RuntimeException("exception");
        }
    }

    @Override
    public String queryByDay(String key, String dimensionKey) {
        Object obj = this.redisTemplate.opsForHash().get(
            getKey(HASH_KEY_PREFIX, key, CumulativeMode.DAY), dimensionKey);
        return String.valueOf(obj);
    }

    @Override
    public String queryByMonth(String key, String dimensionKey) {
        Object obj = this.redisTemplate.opsForHash().get(
            getKey(HASH_KEY_PREFIX, key, CumulativeMode.MONTH), dimensionKey);
        return String.valueOf(obj);
    }

    @Override
    public String queryByYear(String key, String dimensionKey) {
        Object obj = this.redisTemplate.opsForHash().get(
            getKey(HASH_KEY_PREFIX, key, CumulativeMode.YEAR), dimensionKey);
        return String.valueOf(obj);
    }
}
