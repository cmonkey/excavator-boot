package org.excavator.boot.experiment.counter;

public class SynchronizedCounter extends ServerCounter{
    private long count = 0;
    @Override
    public synchronized long getAndReset() {
        long result = count;
        count = 0;
        return result;
    }

    @Override
    public synchronized void add(long increment) {
        count += increment;
    }
}
