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
package org.excavator.boot.experiment.executeShell;

import java.time.Duration;
import java.util.concurrent.*;

public class TaskUtils {
    public static <A> A withTimeout(ExecutorService es, Duration timeout, Callable<A> task)
                                                                                           throws InterruptedException,
                                                                                           TimeoutException {
        final var ft = new FutureTask<>(task);
        try {
            es.submit(ft);
            return ft.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            ft.cancel(true);
            throw new RuntimeException(e);
        } catch (Exception e) {
            ft.cancel(true);
            throw e;
        }
    }

    public static void cancelAll(Future<?>... futures) {
        for (final var f : futures) {
            if (f != null) {
                f.cancel(true);
            }
        }
    }
}
