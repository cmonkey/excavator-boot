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
package org.excavator.boot.idworker.test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdworkerSpringBootStarterApplicationTests {
    private final static Logger logger = LoggerFactory
                                           .getLogger(IdworkerSpringBootStarterApplicationTests.class);

    @Test
    public void contextLoads() {
        logger.info("IdWorker tests running");
    }

    @Test
    public void testDateTime() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
        logger.info("localDateTime = {}", localDateTime.format(fmt));
        logger.info("localDateTime nano = {}", localDateTime.getNano());

        org.joda.time.format.DateTimeFormatter dateTimeFormatter = DateTimeFormat
            .forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dateTimeFormatter.parseDateTime("2017-01-01 00:00:00");

        logger.info("dateTime millis = {}", dateTime.getMillis());

    }
}
