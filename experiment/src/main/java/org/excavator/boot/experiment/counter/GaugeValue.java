package org.excavator.boot.experiment.counter;

public abstract class GaugeValue {
    public abstract long getCount();
    public abstract long getSum();
    public abstract long getMax();
    public abstract long getMin();
}
