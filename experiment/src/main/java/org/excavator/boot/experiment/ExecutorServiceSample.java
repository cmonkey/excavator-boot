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
import java.util.concurrent.ThreadFactory;

public class ExecutorServiceSample {

    public static void main(String[] args) {
        var executor = Executors.newScheduledThreadPool(2, new MyThreadFactory());
        executor.submit(() -> {
            System.out.println("Implemented Runnable Interface " + Thread.currentThread().getName());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.submit(() -> {
            System.out.println("Implemented Runnable Interface " + Thread.currentThread().getName());
        }, 1);

        executor.submit(() -> {
            System.out.println("Implemented Callable Interface " + Thread.currentThread().getName());
            return 1;
        });
        executor.shutdown();
    }
}

class MyThreadFactory implements ThreadFactory {
    static int i = 1;

    @Override
    public Thread newThread(Runnable r) {
        var t = new Thread(r, "" + i);
        i++;
        return t;
    }
}
