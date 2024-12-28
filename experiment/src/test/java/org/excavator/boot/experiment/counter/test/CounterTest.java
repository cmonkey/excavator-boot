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
package org.excavator.boot.experiment.counter.test;

import org.excavator.boot.experiment.counter.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CounterTest {
    @Test
    @DisplayName("counter test by EmptyCounter")
    @Order(1)
    public void testEmptyCounter() throws InterruptedException {
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }

    @Test
    @DisplayName("counter test by TrivialCounter")
    @Order(2)
    public void testTrivialCounter() throws InterruptedException {
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }

    @Test
    @DisplayName("counter test by VolatileCounter")
    @Order(3)
    public void testVolatileCounter() throws InterruptedException {
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }

    @Test
    @DisplayName("counter test by SynchronizedCounter")
    @Order(4)
    public void testSynchronizedCounter() throws InterruptedException {
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }

    @Test
    @DisplayName("counter test by AtomicCounter")
    @Order(5)
    public void testAtomicCounter() throws InterruptedException {
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }

    @Test
    @DisplayName("counter test by CompoundCounter")
    @Order(6)
    public void testCompoundCounter() throws InterruptedException {
        counterTest(new CompoundCounter(TrivialCounter.class), Runtime.getRuntime()
            .availableProcessors(), 0);
        counterTest(new CompoundCounter(TrivialCounter.class), Runtime.getRuntime()
            .availableProcessors(), 10);
        counterTest(new CompoundCounter(TrivialCounter.class), Runtime.getRuntime()
            .availableProcessors(), 50);
        counterTest(new CompoundCounter(TrivialCounter.class), Runtime.getRuntime()
            .availableProcessors(), 100);

        counterTest(new CompoundCounter(VolatileCounter.class), Runtime.getRuntime()
            .availableProcessors(), 0);
        counterTest(new CompoundCounter(VolatileCounter.class), Runtime.getRuntime()
            .availableProcessors(), 10);
        counterTest(new CompoundCounter(VolatileCounter.class), Runtime.getRuntime()
            .availableProcessors(), 50);
        counterTest(new CompoundCounter(VolatileCounter.class), Runtime.getRuntime()
            .availableProcessors(), 100);

        counterTest(new CompoundCounter(SynchronizedCounter.class), Runtime.getRuntime()
            .availableProcessors(), 0);
        counterTest(new CompoundCounter(SynchronizedCounter.class), Runtime.getRuntime()
            .availableProcessors(), 10);
        counterTest(new CompoundCounter(SynchronizedCounter.class), Runtime.getRuntime()
            .availableProcessors(), 50);
        counterTest(new CompoundCounter(SynchronizedCounter.class), Runtime.getRuntime()
            .availableProcessors(), 100);

        counterTest(new CompoundCounter(AtomicCounter.class), Runtime.getRuntime()
            .availableProcessors(), 0);
        counterTest(new CompoundCounter(AtomicCounter.class), Runtime.getRuntime()
            .availableProcessors(), 10);
        counterTest(new CompoundCounter(AtomicCounter.class), Runtime.getRuntime()
            .availableProcessors(), 50);
        counterTest(new CompoundCounter(AtomicCounter.class), Runtime.getRuntime()
            .availableProcessors(), 100);
    }

    public static void counterTest(ServerCounter counter, int nThreads, int delayFactor) throws InterruptedException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        ArrayList<CounterThread> threads = new ArrayList<>();
        for (int i = 0; i < nThreads; i++) {
            threads.add(new CounterThread(counter.getThreadLocalView(), delayFactor, atomicBoolean));
        }

        threads.forEach(CounterThread::start);

        long sum = 0;
        for (int sec = 0; sec < 10; sec++) {
            Thread.sleep(1000);
            long v = counter.getAndReset();
            sum += v;
            System.out.printf("Counter retrieved: %11d\n", v);
        }

        atomicBoolean.set(false);

        for (CounterThread thread : threads) {
            thread.join();
        }

        long lastval = counter.getAndReset();
        sum += lastval;
        System.out.printf("Lastval retrieved: %11d\n", lastval);

        long testsum = 0;
        double nssum = 0;
        for (CounterThread thread:
             threads) {
            double ns = thread.time * 1.0E6 / thread.N;
            System.out.printf("Time = %d; N = %d; time/op: %6.2f ns\n", thread.time, thread.N, ns);
            testsum += thread.N;
            nssum += ns;
        }

        System.out.printf("Time/op avg: %6.2f ns\n", nssum/nThreads);
        System.out.printf("Counter sum: %12d\n", sum);
        System.out.printf("Corrent sum: %12d\n", testsum);
    }
}
