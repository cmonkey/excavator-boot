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

import org.excavator.boot.experiment.executeShell.OSUtils;
import org.excavator.boot.experiment.executeShell.TaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class OSUtilsTest {

    @Test
    @DisplayName("test os utils")
    public void testOsUtils() throws RuntimeException {
        final var es = Executors.newCachedThreadPool();
        try{
            final var r =
                    TaskUtils.withTimeout(es, Duration.ofSeconds(2),
                            () -> OSUtils.executeShellCommand(es, "ls", "-alh"));
            System.out.println(r.stdout());
            System.out.println(r.stderr());
            System.out.println(r.exitCode());
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }finally {
            es.shutdown();
        }
    }
}
