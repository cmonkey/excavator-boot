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
package org.excavator.boot.experiment.stream;

import org.excavator.boot.experiment.stream.Stream;

import java.util.ArrayList;
import java.util.List;

public class CompletableFutureInPractice {

    Stream merge(Stream inputA, Stream inputB) {

        List<Integer> result = new ArrayList<>();

        Integer headA = inputA.next();
        Integer headB = inputA.next();

        while (headA != null || headB != null) {
            if (headA == null || headB != null && headA > headB) {
                result.add(headB);
                headB = inputB.next();
            } else {
                result.add(headA);
                headA = inputA.next();
            }
        }

        return new Stream(result);
    }
}
