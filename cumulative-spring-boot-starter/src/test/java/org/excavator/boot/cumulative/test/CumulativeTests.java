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
package org.excavator.boot.cumulative.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CumulativeApplication.class)
public class CumulativeTests {

    @Resource
    CumulativeService cumulativeService;

    @Test
    public void testCumulativeByDay() {
        String key = "day";
        String dimensionKey = "testDay";
        String value = "10";
        String r = cumulativeService.cumulativeByDay(key, dimensionKey, value);

        assertTrue(Long.valueOf(r) >= Long.valueOf(value));
    }

    @Test
    public void testCumulativeByMonth() {
        String key = "month";
        String dimensionKey = "testMonth";
        String value = "10";
        String r = cumulativeService.cumulativeByMonth(key, dimensionKey, value);

        assertTrue(Long.valueOf(r) >= Long.valueOf(value));
    }

    @Test
    public void testCumulativeByDayAnMonth() {
        String key = "dayAndMonth";
        String dimensionKey = "testDayAndMonth";
        String value = "10";
        cumulativeService.cumulativeByDayAndMonth(key, dimensionKey, value);

        String dayValue = cumulativeService.queryByDay(key, dimensionKey);
        String monthValue = cumulativeService.queryByMonth(key, dimensionKey);

        assertTrue(Long.valueOf(dayValue) >= Long.valueOf(value));
        assertTrue(Long.valueOf(monthValue) >= Long.valueOf(value));
    }

    @Test
    public void testCumulativeByYear() {
        String key = "year";
        String dimensionKey = "testYear";
        String value = "10";
        String r = cumulativeService.cumulativeByYear(key, dimensionKey, value);

        assertTrue(Long.valueOf(r) >= Long.valueOf(value));

        r = cumulativeService.queryByYear(key, dimensionKey);

        assertTrue(Long.valueOf(r) >= Long.valueOf(value));
    }
}
