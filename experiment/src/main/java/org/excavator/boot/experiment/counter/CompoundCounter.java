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
package org.excavator.boot.experiment.counter;

import java.util.ArrayList;

public class CompoundCounter extends ServerCounter {
    private ArrayList<ServerCounter>       views      = new ArrayList<>();
    private Class<? extends ServerCounter> clazz;
    private long                           localValue = 0;

    public CompoundCounter(Class<? extends ServerCounter> clazz) {
        this.clazz = clazz;
    }

    @Override
    public synchronized Counter getThreadLocalView() {
        ServerCounter view = null;
        try {
            view = clazz.newInstance();
            views.add(view);
            return view;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public synchronized long getAndReset() {
        long sum = localValue;
        localValue = 0;
        for (ServerCounter view : views) {
            sum += view.getAndReset();
        }

        return sum;

    }

    @Override
    public synchronized void add(long increment) {
        localValue += increment;
    }
}
