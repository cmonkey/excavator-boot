package org.excavator.boot.lambda.expression.test;

import org.excavator.boot.lambda.expression.MyPredicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyPredicateTest {

    @Test
    @DisplayName("test Predicate")
    public void testPredicate(){
        MyPredicate  myPredicate = new MyPredicate();
        assertEquals(myPredicate.test(10.0), true);
    }
}
