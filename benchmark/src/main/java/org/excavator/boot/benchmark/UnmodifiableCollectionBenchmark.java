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
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 10, time = 5)
@Threads(8)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UnmodifiableCollectionBenchmark {

    private static final long size = 10;
    final static Set<Integer> underlyingCollection = new Random().ints(size).boxed()
            .collect(Collectors.toCollection(HashSet::new));
    final static Set<Integer> unmodifiableCollection = underlyingCollection;
    @Benchmark
    public boolean isEmpty(){
        return unmodifiableCollection.isEmpty();
    }

    @Benchmark
    public void forEach(Blackhole hole){
        unmodifiableCollection.forEach(hole::consume);
    }

    @Benchmark
    public void iterator(Blackhole hole){
        for (Integer i : unmodifiableCollection) {
            hole.consume(i);
        }
    }

    @Benchmark
    public void linearAccess(Blackhole hole){
        List<Integer> list =  unmodifiableCollection();
        for (Integer integer : list) {
            hole.consume(integer);
        }
    }

    private List<Integer> unmodifiableCollection() {
        return (List<Integer>)unmodifiableCollection;
    }

    @Benchmark
    public void randomAccess(Blackhole hole){
        List<Integer> list = unmodifiableCollection();

        for (int at :
                getAts()) {
            hole.consume(list.get(at));
        }
    }

    private int[] getAts() {
        return IntStream.range(0, (int)size).toArray();
    }

    public static void main(String[] args) throws RunnerException {
        Options ops = new OptionsBuilder().include(UnmodifiableCollectionBenchmark.class.getSimpleName()).forks(2)
                .warmupIterations(5).measurementIterations(5).build();

        new Runner(ops).run();
    }
}
