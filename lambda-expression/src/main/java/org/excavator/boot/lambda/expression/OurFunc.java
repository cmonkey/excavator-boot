package org.excavator.boot.lambda.expression;

import java.util.function.Function;

public class OurFunc implements Function<Integer, Double> {

    @Override
    public Double apply(Integer i) {
        return i * 10.0;
    }
}
