package org.excavator.boot.lambda.expression;

import java.util.function.BinaryOperator;

public class MyBinaryOperator implements BinaryOperator<Integer> {

    @Override
    public Integer apply(Integer i, Integer j) {

        return i > j ? i: j;
    }
}
