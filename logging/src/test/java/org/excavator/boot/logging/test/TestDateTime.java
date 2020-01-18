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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDateTime {
    public static final Logger logger = LoggerFactory.getLogger(TestDateTime.class);

    @Test
    @DisplayName("testDateTimeBy2020")
    public void testDateTimeBy2020() {

        long currentTimeMillis = 1577771671190L;
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("simple format date = [{}]", simpleDateFormat.format(date));

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);

        assertEquals(2019, calendar.get(Calendar.YEAR));

        LocalDateTime localDateTime = LocalDateTime.of(2019, 12, 31, 13, 59, 21);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String localDateTimeFormat = localDateTime.format(dateTimeFormatter);
        logger.info("localDateTime format = [{}]", localDateTimeFormat);

        assertEquals("2019", localDateTimeFormat.split("-")[0]);

        LocalDateTime localDateTimeIso8601 = LocalDateTime.of(2019, 12, 31, 13, 59, 21);
        DateTimeFormatter dateTimeFormatterIso8601 = DateTimeFormatter
            .ofPattern("YYYY-MM-dd HH:mm:ss");
        String localDateTimeIso8601Format = localDateTimeIso8601.format(dateTimeFormatterIso8601);
        logger.info("localDateTime iso-8601 format = [{}]", localDateTimeIso8601Format);

        assertEquals("2020", localDateTimeIso8601Format.split("-")[0]);

    }
}
