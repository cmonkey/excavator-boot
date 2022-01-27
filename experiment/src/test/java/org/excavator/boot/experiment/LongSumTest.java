package org.excavator.boot.experiment;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongSumTest {

    @Test
    public void testLongSum() {
        long[] numbers = LongStream.rangeClosed(1, 1000000).toArray();
        LongSum longSum = new LongSum(numbers, 0, numbers.length);
        long sum = new ForkJoinPool().invoke(longSum);
        assertEquals(28000028000000L, sum);
    }
}
