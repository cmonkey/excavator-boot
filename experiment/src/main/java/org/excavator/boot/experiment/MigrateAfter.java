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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MigrateAfter {

    public static void main(String[] args) {
        Boolean bool = Boolean.valueOf(true);
        Byte b = Byte.valueOf("1");
        Character c = Character.valueOf('c');
        Double d = Double.valueOf(1.0);
        Float f = Float.valueOf(1.1f);
        Long l = Long.valueOf(1);
        Short sh = Short.valueOf("12");
        short s3 = 3;
        Short sh3 = Short.valueOf(s3);
        Integer i = Integer.valueOf(1);

    }
    void divide(){
        BigDecimal bd = BigDecimal.valueOf(10);
        BigDecimal bd2 = BigDecimal.valueOf(2);
        bd.divide(bd2, RoundingMode.DOWN);
        bd.divide(bd2, RoundingMode.DOWN);
        bd.divide(bd2,1, RoundingMode.CEILING);
        bd.divide(bd2, 1, RoundingMode.DOWN);
        bd.setScale(2, RoundingMode.DOWN);
    }
}
