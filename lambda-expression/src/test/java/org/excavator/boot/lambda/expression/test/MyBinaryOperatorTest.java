package org.excavator.boot.lambda.expression.test;

import lombok.extern.slf4j.Slf4j;
import org.excavator.boot.lambda.expression.MyBinaryOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class MyBinaryOperatorTest {

    @Test
    @DisplayName("test binaryOperator")
    public void testBinaryOperator(){
        MyBinaryOperator myBinaryOperator = new MyBinaryOperator();

        assertEquals(2, myBinaryOperator.apply(1, 2));
    }
}
