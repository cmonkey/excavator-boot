package org.excavator.boot.lambda.expression;

import java.util.function.BiFunction;

public class MyBiFunction implements BiFunction<String, Integer, Double> {
    @Override
    public Double apply(String s, Integer i) {
        return (s.length() * 10d) / i;
    }
}
