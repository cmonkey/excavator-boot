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
package org.excavator.boot.experiment.counter;

import java.util.concurrent.atomic.AtomicBoolean;

public class CounterThread extends Thread {
    private final Counter counter;
    private final int     delayFactor;

    final AtomicBoolean   atomicBoolean;
    public long           N;
    public long           time;
    double                sum;

    public CounterThread(Counter counter, int delayFactor, AtomicBoolean atomicBoolean) {
        this.counter = counter;
        this.delayFactor = delayFactor;
        this.atomicBoolean = atomicBoolean;
    }

    @Override
    public void run() {
        long N = 0;
        long t0 = System.currentTimeMillis();
        double sum = 0;
        while (atomicBoolean.get()) {
            for (int i = 1; i <= delayFactor; i++) {
                sum += 1.0 / i;
            }
            counter.add();
            ++N;
        }

        long t1 = System.currentTimeMillis();
        time = t1 - t0;
        this.N = N;
        this.sum = sum;
    }
}
