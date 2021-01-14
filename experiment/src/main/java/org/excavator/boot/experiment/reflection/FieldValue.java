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
package org.excavator.boot.experiment.reflection;

import org.apache.commons.lang3.StringUtils;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FieldValue {

    private static final Pattern                           FIELD_SEPARATOR = Pattern.compile("\\.");

    private static final ClassValue<Map<String, Function>> CACHE           = new ClassValue<Map<String, Function>>() {
                                                                               @Override
                                                                               protected Map<String, Function> computeValue(Class<?> type) {
                                                                                   return new ConcurrentHashMap<>();
                                                                               }
                                                                           };

    public static <T> T getFieldValue(Object javaBean, String fieldName) {
        return (T) getCachedFunction(javaBean.getClass(), fieldName).apply(javaBean);
    }

    private static Function getCachedFunction(Class<?> javaBeanClass, String fieldName) {
        final Function function = CACHE.get(javaBeanClass).get(fieldName);

        if (null != function) {
            return function;
        }
        return createAndCacheFunction(javaBeanClass, fieldName);
    }

    private static Function createAndCacheFunction(Class<?> javaBeanClass, String path) {
        return cacheAndGetFunction(path,javaBeanClass, createFunctions(javaBeanClass, path)
        .stream()
        .reduce(Function::andThen)
        .orElseThrow(IllegalStateException::new));
    }

    private static Function cacheAndGetFunction(String path, Class<?> javaBeanClass,
                                                Function functiontoBeCached) {
        Function cachedFunction = CACHE.get(javaBeanClass).putIfAbsent(path, functiontoBeCached);
        return cachedFunction != null ? cachedFunction : functiontoBeCached;
    }

    private static List<Function> createFunctions(Class<?> javaBeanClass, String path){
        List<Function> functions = new ArrayList<>();
        Stream.of(FIELD_SEPARATOR.split(path))
                .reduce(javaBeanClass, (nestedJavaBeanClass, fieldName) -> {
                    Tuple2<? extends Class, Function> getFunction = createFunction(fieldName, nestedJavaBeanClass);
                    functions.add(getFunction._2);
                    return getFunction._1;
                }, (previousClass, nextClass) -> nextClass);
        return functions;
    }

    private static Tuple2<? extends Class, Function> createFunction(String fieldName, Class<?> javaBeanClass){
        return Stream.of(javaBeanClass.getDeclaredMethods())
                .filter(JavaBeanUtil::isGetterMethod)
                .filter(method -> StringUtils.endsWithIgnoreCase(method.getName(), fieldName))
                .map(JavaBeanUtil::createTupleWithReturnTypeAndGetter)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
