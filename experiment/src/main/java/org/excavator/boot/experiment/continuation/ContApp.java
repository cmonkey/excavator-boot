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
package org.excavator.boot.experiment.continuation;

import static java.lang.System.out;

public class ContApp {

    static void add(int a, int b, Cont<Integer> cont) {
        cont.apply(a + b);
    }

    public static void main(String[] args) {
        System.out.println(1 + 2 + 3);
        add(1, 2, partialSum ->
                add(partialSum, 3, sum ->
                        print(sum, unit ->
                                System.exit(0))));
    }

    static void print(int n, Cont<Void> cont) {
        out.println(n);
        cont.apply(null);
    }

    static void iff(boolean expr, Cont<Boolean> trueBranch, Cont<Boolean> falseBranch) {
        if (expr) {
            trueBranch.apply(true);
        } else {
            falseBranch.apply(false);
        }
    }

    static long sum(int from, int to) {
        long sum = 0;
        for (int i = from; i <= to; i++) {
            sum += i;
        }

        return sum;
    }

    static long sum_rec(int from, int to) {
        return (from > to) ? 0 : from + sum_rec(from + 1, to);
    }

}
