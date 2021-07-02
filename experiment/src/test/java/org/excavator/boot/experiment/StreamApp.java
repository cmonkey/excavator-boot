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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamApp {
    public static void main(String[] args) {
        List<String> fruits = List.of("Banana", "Melon", "Watermelon");
        fruits.stream().sorted().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        fruits.stream().sorted().collect(Collectors.toUnmodifiableList());
        Map<Boolean, List<String>> startWithB = fruits.stream().collect(Collectors.partitioningBy(f -> f.startsWith("B")));
        System.out.println("Start with B " + startWithB);
        Map<String, List<String>> initials = fruits.stream().collect(Collectors.groupingBy(s -> s.substring(0)));
        System.out.println("Initials: " + initials);
    }
}
