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

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MergeSortTest {
    public static void main(String[] args) {
        int n = 100000000;
        Random random = ThreadLocalRandom.current();
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = random.nextInt(100);
        }
        Instant start = Instant.now();
        new MergeSort(array, 0, array.length).compute();
        Instant end = Instant.now();
        System.out.println(String.format("%d [msec]", Duration.between(start, end).toMillis()));
    }
}
