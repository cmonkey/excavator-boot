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
package org.excavator.boot.jdk;

import static org.excavator.boot.jdk.ExampleWithNewSwitch.Month.DECEMBER;

public class ExampleWithNewSwitch{
    enum Month{
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    }

    static boolean isWinter(Month month){
        return switch(month){
            case NOVEMBER, DECEMBER, JANUARY -> true;
            default -> false;
        };
    }

    public static void main(String[] args) {
        System.out.printf("%s is winter: %b%n", Month.DECEMBER, isWinter(Month.DECEMBER));
        System.out.printf("%s is winter: %b%n", Month.AUGUST, isWinter(Month.AUGUST));
        System.out.printf("%s is winter: %b%n", Month.NOVEMBER, isWinter(Month.NOVEMBER));
        System.out.printf("%s is winter: %b%n", Month.JUNE, isWinter(Month.JUNE));
    }
}

