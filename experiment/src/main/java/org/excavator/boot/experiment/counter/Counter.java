package org.excavator.boot.experiment.counter;

public abstract class Counter {
    public abstract void add(long increment);

    public void add() {
        add(1);
    }
}
