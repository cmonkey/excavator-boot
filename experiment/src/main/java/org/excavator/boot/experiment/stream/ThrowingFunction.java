package org.excavator.boot.experiment.stream;

public interface ThrowingFunction<T, R> {
    R apply(T t)throws Exception;
}
