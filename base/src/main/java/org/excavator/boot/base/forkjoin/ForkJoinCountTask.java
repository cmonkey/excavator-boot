package org.excavator.boot.base.forkjoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCountTask extends RecursiveTask<Integer> {
    public static final int THRESHOLD = 10;
    private int start;
    private int end;

    public ForkJoinCountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if(canCompute){
            System.out.println(String.format("start to compute from %d to %d", start, end));
            for (int i = 0; i < end; i++) {
                sum += i;
            }
        }else{
            int middle = (start + end ) / 2;
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
