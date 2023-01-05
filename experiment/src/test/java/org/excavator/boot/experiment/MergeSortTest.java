package org.excavator.boot.experiment;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MergeSortTest {
    public static void main(String[] args) {
        int n = 100000000;
        Random random = ThreadLocalRandom.current();
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = random.nextInt(100);
        }
        Instant start = Instant.now();
        new MergeSort(array, 0, array.length).compute();
        Instant end = Instant.now();
        System.out.println(String.format("%d [msec]", Duration.between(start, end).toMillis()));
    }
}
