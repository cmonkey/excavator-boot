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

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorExample {
    public static void main(String[] args) throws InterruptedException {
        var balance = new LongAccumulator(Long::sum, 10000L);
        Runnable w = () -> balance.accumulate(1000L);

        var executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            executor.submit(w);
        }

        executor.shutdown();
        if(executor.awaitTermination(1000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {  // wait for all tasks to finish
            System.out.println("balance: " + balance.get());
        }

    }
}
