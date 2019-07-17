package org.excavator.boot.lambda.expression;

import java.util.function.IntFunction;

public class MyIntFunction implements IntFunction<String> {
    @Override
    public String apply(int i) {
        return String.valueOf(i * 10);
    }
}
