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
package org.excavator.boot.lambda.expression.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ExpressionTest {

    @Test
    @DisplayName("test lambda expression")
    public void testLambdaExpression(){
        Function<Integer, Double> ourFunc = i -> i * 10.0;

        assertEquals(10,0, ourFunc.apply(1));

        Consumer<String> consumer = s -> log.info("This {} is consumed", s);

        consumer.accept("Hello");

        Supplier<String> supplier = () -> "Success";

        assertEquals("Success", supplier.get());

        Predicate<Double> predicate = num -> num == 10.0;

        assertEquals(true, predicate.test(10.0));

        IntFunction<String> intFunction = i -> String.valueOf(i * 10);

        assertEquals("10", intFunction.apply(1));

        BiFunction<String, Integer, Double> biFunction = (s, i) -> (s.length() * 10.d) / i;

        assertEquals(biFunction.apply("foo", 10), 3);

        BinaryOperator<Integer> binaryOperator = (i, j) -> i > j ? i:j;

        assertEquals(binaryOperator.apply(1, 2), 2);

        IntBinaryOperator intBinaryOperator = (i, j) -> i > j ? i: j;

        assertEquals(intBinaryOperator.applyAsInt(1, 2), 2);
    }
}
