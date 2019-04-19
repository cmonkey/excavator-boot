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
package org.excavator.boot.logging.test;

import org.excavator.boot.logging.LogProducingService;
import org.excavator.boot.logging.StaticAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStaticAppender {

    @BeforeEach
    public void clearLoggingStatements() {
        StaticAppender.clearEvents();
    }

    @Test
    public void testAssertingLoggingStatemenetsA() {
        LogProducingService service = new LogProducingService();
        service.writeSomeLoggingStatemenets("A");

        assertThat(StaticAppender.getEvents()).extracting("message").containsOnly(
            "Let's assert some logs! A", "this message is in a separate thread");
    }

    @Test
    public void testAssertingLoggingStatemenetsB() {
        LogProducingService service = new LogProducingService();
        service.writeSomeLoggingStatemenets("B");

        assertThat(StaticAppender.getEvents()).extracting("message").containsOnly(
            "Let's assert some logs! B", "this message is in a separate thread");
    }
}
