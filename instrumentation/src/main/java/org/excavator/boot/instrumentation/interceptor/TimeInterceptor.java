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
package org.excavator.boot.instrumentation.interceptor;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.excavator.boot.instrumentation.annoation.TraceTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TimeInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);

    @RuntimeType
    public static Object interceptor(@Origin Class<?> clazz, @Origin Method method,
                                     @SuperCall Callable<?> callable) throws Exception {
        logger.info("NewTimeInterceptor interceptor param clazz = {}, method = {}, callable = {}",
            clazz, method, callable);

        TraceTime traceTime = method.getAnnotation(TraceTime.class);

        if (null == traceTime) {
            return callable.call();
        }

        long startTime = System.currentTimeMillis();

        try {
            return callable.call();
        } finally {
            logger.info("clazz simpleName = {}, method = {}, cost = {} ms", clazz.getSimpleName(),
                method.getName(), System.currentTimeMillis() - startTime);
        }
    }
}
