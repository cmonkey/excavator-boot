package org.excavator.boot.lambda.expression.test;

import lombok.extern.slf4j.Slf4j;
import org.excavator.boot.lambda.expression.MyIntFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class MyIntFunctionTest {

    @Test
    @DisplayName("test IntFunction")
    public void testIntFunction(){
        MyIntFunction myIntFunction = new MyIntFunction();

        assertEquals("10", myIntFunction.apply(1));
    }
}
