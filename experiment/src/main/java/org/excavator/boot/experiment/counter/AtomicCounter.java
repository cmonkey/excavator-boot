package org.excavator.boot.experiment.counter;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter extends ServerCounter{
    private AtomicLong count = new AtomicLong(0);
    @Override
    public void add(long increment) {
        count.addAndGet(increment);
    }

    @Override
    public long getAndReset() {
        return count.getAndSet(0);
    }
}
