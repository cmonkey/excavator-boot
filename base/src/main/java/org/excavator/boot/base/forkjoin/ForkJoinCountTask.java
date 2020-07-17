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
package org.excavator.boot.base.forkjoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCountTask extends RecursiveTask<Integer> {
    public static final int THRESHOLD = 10;
    private int             start;
    private int             end;

    public ForkJoinCountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            System.out.println(String.format("start to compute from %d to %d", start, end));
            for (int i = start; i <= end; ++i) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            ForkJoinCountTask left = new ForkJoinCountTask(start, middle);
            ForkJoinCountTask right = new ForkJoinCountTask(middle + 1, end);

            left.fork();
            right.fork();

            Integer leftResult = left.join();
            Integer rightResult = right.join();

            sum = leftResult + rightResult;
        }
        return sum;
    }
}
