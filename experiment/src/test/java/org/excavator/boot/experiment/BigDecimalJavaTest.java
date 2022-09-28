package org.excavator.boot.experiment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigDecimalJavaTest {
    @Test
    @DisplayName("test bigDecimal")
    public void testBigDecimal(){
        var v1 = "123456789012345678.89";
        var v2 = "987654321012341342.03";
        var mathContext = MathContext.DECIMAL128;
        /**
        var b1 = new BigDecimal(Double.toString(v1), mathContext);
        var b2 = new BigDecimal(Double.toString(v2), mathContext);
         */
         var b1  = new BigDecimal(v1);
        var b2  = new BigDecimal(v2);
        var r = b1.add(b2);
        assertEquals("1111111110024687020.92", r.stripTrailingZeros().toPlainString());
    }
}
