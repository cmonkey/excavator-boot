package org.excavator.boot.experiment.counter;

public class VolatileCounter extends ServerCounter{
    private volatile long count = 0;
    @Override
    public long getAndReset() {
        long result = count;
        count = 0;
        return result;
    }

    @Override
    public void add(long increment) {
        count += increment;
    }
}
