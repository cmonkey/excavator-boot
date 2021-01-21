package org.excavator.boot.experiment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyFunctionInterfaceTest {

    @Test
    @DisplayName("testMyFunctionInterface")
    public void testMyFunctionInterface(){

        MyFunctionInterface<String> functionInterface= s -> s.toUpperCase(Locale.getDefault());

        var str = "aaa";
        var r = functionInterface.getValue(str);
        assertEquals("AAA", r);
    }
}
