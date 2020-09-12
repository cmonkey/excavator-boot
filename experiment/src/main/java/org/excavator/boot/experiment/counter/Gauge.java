package org.excavator.boot.experiment.counter;

public abstract class Gauge {
    public abstract void report(long value);
}
