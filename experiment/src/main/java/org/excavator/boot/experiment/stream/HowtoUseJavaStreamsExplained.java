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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HowtoUseJavaStreamsExplained {

    protected static int example1(int numberOfMultiples){
        return Stream.iterate(4, multipleOfFour -> multipleOfFour + 4)
                .limit(numberOfMultiples)
                .map(multipleOfFour -> multipleOfFour * multipleOfFour)
                .filter(multipleOfFourSquared -> multipleOfFourSquared % 10 == 0)
                .reduce(0, Integer::sum);
    }

    protected static Stream<Map.Entry<Integer, Integer>> example2() {
        List<Integer> arrayList = new ArrayList<>(Arrays.asList(1, 2, 3));
        Stream<Integer> intStream = arrayList.stream();

        Map<Integer, Integer> map = new HashMap<Integer, Integer>() {
            {
                put(1, 1);
                put(2, 2);
                put(3, 3);
            }
        };

        Stream<Map.Entry<Integer, Integer>> mapStream = map.entrySet().stream();
        ;

        return mapStream;
    }

    protected static List<int[]> example3(){
        List<int[]> list = new ArrayList<>(){{
            add(new int[]{1,20});
            add(new int[]{5,15});
            add(new int[]{7,18});
            add(new int[]{3,25});
        }};
        list = list.stream().sorted(Comparator.comparing(i -> i [1]))
                .collect(Collectors.toList());

        return list;
    }
}
