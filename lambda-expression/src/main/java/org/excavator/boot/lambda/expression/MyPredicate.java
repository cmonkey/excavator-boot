package org.excavator.boot.lambda.expression;

import java.util.function.Predicate;

public class MyPredicate implements Predicate<Double> {
    @Override
    public boolean test(Double num) {
        return num == 10.0;
    }
}
