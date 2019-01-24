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
package org.excavator.boot.netty.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuavaBloomFilterTest {

    private static final Logger logger = LoggerFactory.getLogger(GuavaBloomFilterTest.class);

    @Test
    @DisplayName("guaveBloomFilterTest")
    public void testGuavaBloomFilter() {
        long startTime = System.currentTimeMillis();

        int num = 10000000;
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), num, 0.01);

        for (int i = 0; i < num; i++) {
            filter.put(i);
        }

        assert (filter.mightContain(1));
        assert (filter.mightContain(2));
        assert (filter.mightContain(3));
        assert (filter.mightContain(num / 10));

        logger.info("execute time = {}", System.currentTimeMillis() - startTime);
    }
}
