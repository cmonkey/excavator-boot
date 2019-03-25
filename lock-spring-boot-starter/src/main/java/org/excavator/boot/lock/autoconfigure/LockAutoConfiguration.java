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
package org.excavator.boot.lock.autoconfigure;

import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.lang3.StringUtils;
import org.excavator.boot.lock.config.LockConfig;
import org.excavator.boot.lock.enums.RedissonMode;
import org.excavator.boot.lock.handler.LockAspectHandler;
import org.excavator.boot.lock.provider.BusinessKeyProvider;
import org.excavator.boot.lock.provider.LockInfoProvider;
import org.excavator.boot.lock.service.LockFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(LockConfig.class)
@Import({ LockAspectHandler.class })
public class LockAutoConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(LockAutoConfiguration.class);

    @Resource
    private LockConfig lockConfig;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    RedissonClient redisson() throws Exception {
        Config config = new Config();
        LockConfig.Redis lockConfigRedis = lockConfig.getRedis();

        RedissonMode redissonMode = getRedissonMode(lockConfigRedis.getMode());

        switch (redissonMode){
            case SINGLE:{
                config.useSingleServer().setPassword(lockConfigRedis.getSingle().getPassword())
                .setAddress(lockConfigRedis.getSingle().getAddress());
                break;
            }
            case SENTINEL:{
                config.useSentinelServers().setMasterName(lockConfigRedis.getSentinel().getMaster())
                        .addSentinelAddress(lockConfigRedis.getSentinel().getAddress());
                break;
            }
            case CLUSTER:{
                config.useClusterServers().addNodeAddress(lockConfigRedis.getCluster().getAddress());
                break;
            }
        }

        Codec codec = (Codec) ClassUtils.forName(lockConfigRedis.getCodec(),
            ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        config.setEventLoopGroup(new NioEventLoopGroup());

        return Redisson.create(config);
    }

    private RedissonMode getRedissonMode(String mode) {
        if(StringUtils.isBlank(mode)){
            mode = "single";
        }

        try{
            return RedissonMode.valueOf(mode.toUpperCase());
        }catch (Exception e){
            logger.error("getRedissonMode support RedissonMode (single, sentinel, cluster) and support not Exception = {}", e);
            throw new UnsupportedOperationException(e);
        }
    }

    @Bean
    public LockInfoProvider lockInfoProvider() {
        return new LockInfoProvider();
    }

    @Bean
    public BusinessKeyProvider businessKeyProvider() {
        return new BusinessKeyProvider();
    }

    @Bean
    public LockFactory lockFactory() {
        return new LockFactory();
    }
}
