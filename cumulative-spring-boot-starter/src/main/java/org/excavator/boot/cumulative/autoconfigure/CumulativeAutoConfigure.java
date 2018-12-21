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
package org.excavator.boot.cumulative.autoconfigure;

import org.excavator.boot.cumulative.service.Cumulative;
import org.excavator.boot.cumulative.service.RedisCumulative;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.IOException;

@Configuration
public class CumulativeAutoConfigure {

    @Bean
    public Cumulative redisCumulative(@Qualifier("stringRedisTemplate") RedisTemplate<String, String> redisTemplate)
                                                                                                                    throws IOException {
        return new RedisCumulative(redisTemplate, cumulativeScript());
    }

    @Bean(name = "cumulativeScript")
    @ConditionalOnMissingBean(name = "cumulativeScript")
    public DefaultRedisScript<Long> cumulativeScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();

        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(
            "lua/cumulative.lua")));

        return defaultRedisScript;
    }
}