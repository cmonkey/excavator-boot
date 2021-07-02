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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListApp {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>(List.of("Bannas", "Melon", "Watermelon"));
        fruits.forEach(System.out::println);
        fruits.removeIf("Bannas"::equals);
        fruits.sort(Comparator.naturalOrder());
        System.out.println("After sort: ");
        fruits.forEach(System.out::println);
    }
}
