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
import java.util.concurrent.Executors;

public class ExecutorServiceSample3 {
    public static void main(String[] args) {
        var executor = Executors.newScheduledThreadPool(2, new MyThreadFactory());

        //no return value
        CompletableFuture.runAsync(() -> {
            System.out.println("Inside Runnable " + Thread.currentThread().getName());
        }, executor);

        //with return value
        var cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("Inside Supplier " + Thread.currentThread().getName());
            return true;
        },executor);

        //accept the new Consumer object once the future is completed
        cf.thenAccept(result -> {
            if(result) {
                System.out.println("Inside thenAccept");
            }
        });

        //apply a Function object once the future is completed and returns boolean
        var applyResult = cf.thenApply(result -> {
            System.out.println("Inside thenApply");
            return false;
        });

        //shutdown executor manually
        executor.shutdown();
    }
}
