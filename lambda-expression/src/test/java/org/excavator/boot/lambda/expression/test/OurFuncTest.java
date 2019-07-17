package org.excavator.boot.lambda.expression.test;

import org.excavator.boot.lambda.expression.OurFunc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OurFuncTest {

    @Test
    @DisplayName("test our func")
    public void testOurFunc(){
        OurFunc ourFunc = new OurFunc();
        assertEquals(10.0, ourFunc.apply(1));
    }
}
