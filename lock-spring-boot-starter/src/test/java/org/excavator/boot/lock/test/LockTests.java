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
package org.excavator.boot.lock.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LockTestApplication.class)
public class LockTests {
    private final static Logger logger = LoggerFactory.getLogger(LockTests.class);

    @Autowired
    TestLockService             testLockService;

    /**
     * 同一进程内多线程获取锁测试
     * @throws Exception
     */
    @Test
	public void multithreadingTest()throws Exception{
        int nThreads = Runtime.getRuntime().availableProcessors();

		ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

		List<Callable<Object>> tasks = new ArrayList<>(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Callable<Object> callable = () -> testLockService.getValue("test");
            tasks.add(callable);
        }

        List<Future<Object>> futures = executorService.invokeAll(tasks);

        long count = futures.stream().map(future -> {
            Object obj = null;
            try {
                obj = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            return obj;
        }).filter(Objects::nonNull).count();

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        assertEquals(count, nThreads);

	}
}
