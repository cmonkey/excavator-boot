package org.excavator.boot.experiment.counter;

import java.util.concurrent.atomic.AtomicBoolean;

public class CounterThread extends Thread{
    private final Counter counter;
    private final int delayFactor;

    final AtomicBoolean atomicBoolean;
    public long N;
    public long time;
    double sum;

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
        while(atomicBoolean.get()){
            for (int i = 1; i <= delayFactor; i++) {
                sum += 1.0/i;
            }
            counter.add();
            ++ N;
        }

        long t1 = System.currentTimeMillis();
        time = t1 - t0;
        this.N = N;
        this.sum = sum;
    }
}
