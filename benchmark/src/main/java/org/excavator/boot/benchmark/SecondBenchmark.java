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

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class SecondBenchmark {

    @Param({ "10000", "100000", "1000000" })
    private int        length;
    private int[]      numbers;

    private Calculator singelThreadCalculator;
    private Calculator multiThreadCalculator;

    @Setup
    public void prepare() {
        numbers = IntStream.rangeClosed(1, length).toArray();
        singelThreadCalculator = new SinglethreadCalculator();
        multiThreadCalculator = new MultithreadCalculator(Runtime.getRuntime()
            .availableProcessors());
    }

    @TearDown
    public void shutdown() {
        singelThreadCalculator.shutdown();
        multiThreadCalculator.shutdown();
    }

    @Benchmark
    public long singleThreadCalculator() {
        return singelThreadCalculator.sum(numbers);
    }

    @Benchmark
    public long multiThreadCalculator() {
        return multiThreadCalculator.sum(numbers);
    }

    public static void main(String[] args) throws RunnerException {
        Options ops = new OptionsBuilder().include(SecondBenchmark.class.getSimpleName()).forks(2)
            .warmupIterations(5).measurementIterations(5).build();

        new Runner(ops).run();
    }
}
