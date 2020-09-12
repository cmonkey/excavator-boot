package org.excavator.boot.experiment.counter;

import org.excavator.boot.experiment.counter.Counter;

public class CounterThread extends Thread{
    private final Counter counter;
    private final int delayFactor;

    static volatile boolean running = true;
    long N;
    long time;
    double sum;

    public CounterThread(Counter counter, int delayFactor) {
        this.counter = counter;
        this.delayFactor = delayFactor;
    }

    @Override
    public void run() {
        long N = 0;
        long t0 = System.currentTimeMillis();
        double sum = 0;
        while(running){
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
