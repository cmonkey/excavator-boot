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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.excavator.boot.experiment.snowflake.Snowflake;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.*;

public class SnowflakeTest {

    @Test
    public void nextId_shouldGenerateIdWithCorrectBitsFilled() {
        Snowflake snowflake = new Snowflake(784);

        long beforeTimestamp = Instant.now().toEpochMilli();

        long id = snowflake.nextId();

        // Validate different parts of the Id
        long[] attrs = snowflake.parse(id);
        assertTrue(attrs[0] >= beforeTimestamp);
        assertEquals(784, attrs[1]);
        assertEquals(0, attrs[2]);
    }

    @Test
    public void nextId_shouldGenerateUniqueId() {
        Snowflake snowflake = new Snowflake(234);
        int iterations = 5000;

        // Validate that the IDs are not same even if they are generated in the same ms
        long[] ids = new long[iterations];
        for (int i = 0; i < iterations; i++) {
            ids[i] = snowflake.nextId();
        }

        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) {
                assertFalse(ids[i] == ids[j]);
            }
        }
    }

    @Test
    public void nextId_shouldGenerateUniqueIdIfCalledFromMultipleThreads() throws InterruptedException, ExecutionException  {
        int numThreads = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        Snowflake snowflake = new Snowflake(234);
        int iterations = 10000;

        // Validate that the IDs are not same even if they are generated in the same ms in different threads
        Future<Long>[] futures = new Future[iterations];
        for(int i = 0; i < iterations; i++) {
            futures[i] =  executorService.submit(() -> {
                long id = snowflake.nextId();
                latch.countDown();;
                return id;
            });
        }

        latch.await();
        for(int i = 0; i < futures.length; i++) {
            for(int j = i+1; j < futures.length; j++) {
                assertFalse(futures[i].get() == futures[j].get());
            }
        }
    }
}