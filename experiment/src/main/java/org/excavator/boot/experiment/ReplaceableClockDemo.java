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

import java.time.Clock;
import java.time.LocalDateTime;

public class ReplaceableClockDemo {

    public static void main(String[] args) {
        ReplaceableClockDemo demo = new ReplaceableClockDemo(Clock.systemUTC());
        demo.isNowBefore(LocalDateTime.of(2017, 3, 1, 12, 0));

    }

    private final Clock clock;

    public ReplaceableClockDemo(Clock clock) {
        this.clock = clock;
    }

    public boolean isNowBefore(LocalDateTime dateTime) {
        return LocalDateTime.now(clock).isBefore(dateTime);
    }
}
