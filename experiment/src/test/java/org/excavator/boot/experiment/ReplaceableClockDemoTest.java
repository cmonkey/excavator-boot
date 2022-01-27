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

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReplaceableClockDemoTest {

    @Test
    public void shouldBeBeforeStPartickDay2017() throws Exception {
        Clock now = Clock.fixed(LocalDateTime.of(2017, 2, 15, 12, 34).toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC"));
        LocalDateTime stPatricksDay = LocalDateTime.of(2017, 3, 17, 0, 0);
        ReplaceableClockDemo demo = new ReplaceableClockDemo(now);
        assertTrue(demo.isNowBefore(stPatricksDay));
    }

    @Test
    public void shouldNotBeBeforePiDay2017() throws Exception {
        Clock now = Clock.fixed(LocalDateTime.of(2017, 5, 18, 9, 45).toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC"));
        LocalDateTime piDay = LocalDateTime.of(2017, 3, 14, 0, 0);
        ReplaceableClockDemo demo = new ReplaceableClockDemo(now);
        assertFalse(demo.isNowBefore(piDay));
    }
}
