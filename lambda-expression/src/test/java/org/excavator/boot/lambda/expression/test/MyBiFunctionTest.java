package org.excavator.boot.lambda.expression.test;

import lombok.extern.slf4j.Slf4j;
import org.excavator.boot.lambda.expression.MyBiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class MyBiFunctionTest {

    @Test
    @DisplayName("test bi function")
    public void testBiFunction(){

        MyBiFunction myBiFunction = new MyBiFunction();

        assertEquals(3, myBiFunction.apply("foo", 10));
    }
}
