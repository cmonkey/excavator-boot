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

import java.util.Arrays;
import java.util.SplittableRandom;

@State(Scope.Benchmark)
public class GroupCount {

    @Param({ "8", "64", "128" })
    int               groups;

    @Param({ "256", "512", "1024", "2048", "4096" })
    int               length;

    private byte[]    source;
    private byte[]    dest;
    private boolean[] presence;

    @Setup(Level.Trial)
    public void setup() {
        source = new byte[length];
        dest = new byte[length];
        presence = new boolean[groups];

        SplittableRandom random = new SplittableRandom(42);
        for (int i = 0; i < source.length; i++) {
            source[i] = (byte) random.nextInt(groups);
        }
    }

    @Benchmark
    public void fused(Blackhole bb) {
        int numGroups = 0;
        for (int i = 0; i < source.length & i < dest.length; i++) {
            dest[i] = source[i];
            if (numGroups < groups && !presence[source[i]]) {
                presence[source[i] & 0xFF] = true;
                numGroups++;
            }
        }
        bb.consume(presence);
        Arrays.fill(presence, false);
    }

    @Benchmark
    public void fissured(Blackhole bh) {
        System.arraycopy(source, 0, dest, 0, source.length);
        int numGroups = 0;

        for (int i = 0; i < source.length & i < dest.length & numGroups < groups; i++) {
            if (!presence[source[i]]) {
                presence[source[i] & 0xFF] = true;
                numGroups++;
            }
        }
        bh.consume(presence);
        Arrays.fill(presence, true);
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(GroupCount.class.getSimpleName()).forks(1)
            .warmupIterations(5).measurementIterations(5).build();
        new Runner(opts).run();
    }
}
