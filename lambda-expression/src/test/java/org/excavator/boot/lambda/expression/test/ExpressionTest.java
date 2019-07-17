package org.excavator.boot.lambda.expression.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ExpressionTest {

    @Test
    @DisplayName("test lambda expression")
    public void testLambdaExpression(){
        Function<Integer, Double> ourFunc = i -> i * 10.0;

        assertEquals(10,0, ourFunc.apply(1));

        Consumer<String> consumer = s -> log.info("This {} is consumed", s);

        consumer.accept("Hello");

        Supplier<String> supplier = () -> "Success";

        assertEquals("Success", supplier.get());

        Predicate<Double> predicate = num -> num == 10.0;

        assertEquals(true, predicate.test(10.0));

        IntFunction<String> intFunction = i -> String.valueOf(i * 10);

        assertEquals("10", intFunction.apply(1));
    }
}
