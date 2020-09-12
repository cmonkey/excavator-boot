package org.excavator.boot.experiment.counter;

public class EmptyCounter extends ServerCounter{
    @Override
    public long getAndReset() {
        return 0;
    }

    @Override
    public void add(long increment) {

    }
}
