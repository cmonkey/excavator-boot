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

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

@State(Scope.Thread)
public class BenchmarkStream {

    public static int[] ints;

    @Setup
    public void setup() {
        ints = new int[1000_000_00];

        Random random = new Random();

        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt();
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    public static void forLoop() {

        int[] a = ints;
        int e = a.length;
        int m = Integer.MIN_VALUE;

        for (int i = 0; i < e; i++) {
            if (a[i] > m) {
                m = a[i];
            }
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    public static void stream(){
        final int reduce = IntStream.of(ints).reduce(Integer.MIN_VALUE, Math::max);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Main.main(args);
    }
}
