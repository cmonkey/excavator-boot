package org.excavator.boot.experiment;

@FunctionalInterface
public interface MyFunctionInterface<T> {

    public T getValue(T t);
}
