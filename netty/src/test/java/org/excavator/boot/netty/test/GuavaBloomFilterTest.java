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

        for(int i = 0; i < num; i++){
            filter.put(i);
        }

        assert(filter.mightContain(1));
        assert(filter.mightContain(2));
        assert(filter.mightContain(3));
        assert(filter.mightContain(num/10));

        logger.info("execute time = {}", System.currentTimeMillis() - startTime);
    }
}
