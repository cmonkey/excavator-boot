package org.excavator.boot.experiment;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorExample {
    public static void main(String[] args) throws InterruptedException {
        var balance = new LongAccumulator(Long::sum, 10000L);
        Runnable w = () -> balance.accumulate(1000L);

        var executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            executor.submit(w);
        }

        executor.shutdown();
        if(executor.awaitTermination(1000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {  // wait for all tasks to finish
            System.out.println("balance: " + balance.get());
        }

    }
}
