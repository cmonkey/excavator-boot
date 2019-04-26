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
package org.excavator.boot.instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.excavator.boot.instrumentation.interceptor.TimeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class ByteBuddyHelper {
    private final static Logger logger = LoggerFactory.getLogger(ByteBuddyHelper.class);

    public static void premain(String agentArgs, Instrumentation instrumentation) {

        logger.info("ByteBuddyHelper premain agentArgs = {}", agentArgs);

        transformer(instrumentation);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {

        logger.info("ByteBuddyHelper agent agentArgs = {}", agentArgs);

        transformer(instrumentation);
    }

    public static void transformer(Instrumentation instrumentation){
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, module) -> builder.method(ElementMatchers.any())
                .intercept(MethodDelegation.to(TimeInterceptor.class));

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }

            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {

            }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }
        };

        new AgentBuilder.Default().type(ElementMatchers.nameStartsWith("org.excavator.boot"))
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);
    }
}
