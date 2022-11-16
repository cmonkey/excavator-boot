/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.experiment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigDecimalJavaTest {
    @Test
    @DisplayName("test bigDecimal")
    public void testBigDecimal() {
        var v1 = "123456789012345678.89";
        var v2 = "987654321012341342.03";
        var mathContext = MathContext.DECIMAL128;
        /**
        var b1 = new BigDecimal(Double.toString(v1), mathContext);
        var b2 = new BigDecimal(Double.toString(v2), mathContext);
         */
        var b1 = new BigDecimal(v1);
        var b2 = new BigDecimal(v2);
        var r = b1.add(b2);
        assertEquals("1111111110024687020.92", r.stripTrailingZeros().toPlainString());
    }
}
