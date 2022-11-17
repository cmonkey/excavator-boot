package org.excavator.boot.experiment.continuation;

@FunctionalInterface
public interface Cont<R> {
    void apply(R result);
}
