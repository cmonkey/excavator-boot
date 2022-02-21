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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ExecutorServiceSample4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newScheduledThreadPool(2, new MyThreadFactory());
        var cf = CompletableFuture.supplyAsync(() -> 1,executor);
        //result of cf is passed to another future object that invokes add method
        var result = cf.thenCompose(x -> CompletableFuture.supplyAsync(() -> add(x, 2), executor));
        System.out.println("Result " + result.get());

        //shutdown executor manually
        executor.shutdown();
    }

    public static int add(int x, int y) {
        return x + y;
    }
}
