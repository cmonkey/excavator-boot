package org.excavator.boot.experiment.counter.test;

import lombok.SneakyThrows;
import org.excavator.boot.experiment.counter.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CounterTest {
    @Test
    @DisplayName("counter test by EmptyCounter")
    @Order(1)
    public void testEmptyCounter() {
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new EmptyCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }

    @Test
    @DisplayName("counter test by TrivialCounter")
    @Order(2)
    public void testTrivialCounter(){
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new TrivialCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }
    @Test
    @DisplayName("counter test by VolatileCounter")
    @Order(3)
    public void testVolatileCounter(){
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new VolatileCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }
    @Test
    @DisplayName("counter test by SynchronizedCounter")
    @Order(4)
    public void testSynchronizedCounter(){
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new SynchronizedCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }
    @Test
    @DisplayName("counter test by AtomicCounter")
    @Order(5)
    public void testAtomicCounter(){
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 0);
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 10);
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 50);
        counterTest(new AtomicCounter(), Runtime.getRuntime().availableProcessors(), 100);
    }
    @SneakyThrows
    public static void counterTest(ServerCounter counter, int nThreads, int delayFactor){
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        ArrayList<CounterThread> threads = new ArrayList<>();
        for (int i = 0; i < nThreads; i++) {
            threads.add(new CounterThread(counter, delayFactor, atomicBoolean));
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
