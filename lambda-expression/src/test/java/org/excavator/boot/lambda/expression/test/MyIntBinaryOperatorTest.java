package org.excavator.boot.lambda.expression.test;

import lombok.extern.slf4j.Slf4j;
import org.excavator.boot.lambda.expression.MyIntBinaryOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class MyIntBinaryOperatorTest {

    @Test
    @DisplayName("test intBinaryOperator")
    public void testIntBinaryOperator(){
        MyIntBinaryOperator myIntBinaryOperator = new MyIntBinaryOperator();

        assertEquals(2, myIntBinaryOperator.applyAsInt(1, 2));
    }
}
