package org.excavator.boot.lambda.expression.test;

import org.excavator.boot.lambda.expression.MySupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MySupplierTest {

    @Test
    @DisplayName("test supplier")
    public void testSupplier(){
        MySupplier mySupplier = new MySupplier();

        assertEquals("Success", mySupplier.get());
    }
}
