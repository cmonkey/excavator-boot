package org.excavator.boot.lambda.expression.test;

import org.excavator.boot.lambda.expression.MyConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MyConsumerTest {

    @Test
    @DisplayName("test consumer accept")
    public void testConsumer(){
        MyConsumer myConsumer = new MyConsumer();
        myConsumer.accept("Hello");
    }
}
