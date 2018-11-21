package org.excavator.boot.benchmark;

public class SinglethreadCalculator implements Calculator{
    @Override
    public long sum(int[] numbers) {
        long total = 0L;
        for (int i = 0; i < numbers.length; i++) {
            total += numbers[i];
        }

        return total;
    }

    @Override
    public void shutdown() {

    }
}
