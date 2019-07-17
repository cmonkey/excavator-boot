package org.excavator.boot.lambda.expression;

import java.util.function.Supplier;

public class MySupplier implements Supplier<String> {
    @Override
    public String get() {
        return "Success";
    }
}
