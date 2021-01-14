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
package org.excavator.boot.experiment.reflection.test;

import org.openjdk.jmh.annotations.*;

import java.beans.JavaBean;
import java.util.concurrent.TimeUnit;

@Fork(3)
@Warmup(iterations = 5, time = 3)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class JavaBeanUtilBenchmark {

    @Param({ "fieldA", "nestedJavaBean.fieldA", "nestedJavaBean.nestedJavaBean.fieldA",
            "nestedJavaBean.nestedJavaBean.nestedJavaBean.fieldA" })
    String   fieldName;
    JavaBean javaBean;

    @Setup
    public void setup() {
    }
}
