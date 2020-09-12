package org.excavator.boot.experiment.counter;

public abstract class ServerCounter extends Counter{
    public abstract long getAndReset();
}
