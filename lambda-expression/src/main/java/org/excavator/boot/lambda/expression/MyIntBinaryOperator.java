package org.excavator.boot.lambda.expression;

import java.util.function.IntBinaryOperator;

public class MyIntBinaryOperator implements IntBinaryOperator {
    @Override
    public int applyAsInt(int i, int j) {
        return i > j? i: j;
    }
}
