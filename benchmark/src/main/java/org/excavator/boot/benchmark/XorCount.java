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
package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
public class XorCount {

    @Param({ "256", "512", "1024", "2048", "4096" })
    int            size;
    private long[] left;
    private long[] right;

    @Setup(Level.Trial)
    public void setup() {
        left = new long[size];
        right = new long[size];

        for (int i = 0; i < size; i++) {
            left[i] = ThreadLocalRandom.current().nextLong();
            right[i] = ThreadLocalRandom.current().nextLong();
        }
    }

    @Benchmark
    public int fused() {
        int count = 0;
        for (int i = 0; i < left.length & i < right.length; i++) {
            left[i] ^= right[i];
            count += Long.bitCount(left[i]);
        }

        return count;
    }

    @Benchmark
    public int fissured() {
        for (int i = 0; i < left.length & i < right.length; i++) {
            left[i] ^= right[i];
        }

        int count = 0;
        for (long l : left) {
            count += Long.bitCount(l);
        }

        return count;
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(XorCount.class.getSimpleName()) // 测试类的名字，可以通过正则表达式进行匹配
            .forks(1) // fork 次数，fork 多少个进程进行测试
            .warmupIterations(5) // 预热的迭代次数
            .measurementIterations(5) // 实际测量的次数
            .build();
        new Runner(opts).run();
    }
}
