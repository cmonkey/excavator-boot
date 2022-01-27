package org.excavator.boot.experiment;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class LongSum extends RecursiveTask<Long> {

    private static final int LIMIT = 1000;

    private long[] numbers;
    private int start;
    private int end;

    public LongSum(long[] numbers, int start, int end){
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute(){
        int length = end - start;
        if(length <= LIMIT){
            return computeSequentially();
        }

        LongSum left = new LongSum(numbers, start, length / 2);
        left.fork();

        LongSum right = new LongSum(numbers, start + length /2, end);
        Long rightResult = right.compute();
        Long leftResult = left.join();

        return leftResult + rightResult;
    }

    private Long computeSequentially(){
        return Arrays.stream(numbers).sum();
    }
}
