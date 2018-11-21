package org.excavator.boot.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultithreadCalculator implements Calculator{
    private final int nThreads;
    private final ExecutorService pool;

    public MultithreadCalculator(int nThreads) {
        this.nThreads = nThreads;
        this.pool = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public long sum(int[] numbers) {
        int chunk = numbers.length / nThreads;
        int from,to;

        List<SubTask> tasks = new ArrayList<>();

        for (int i = 1; i <= nThreads; i++) {
            if(i == nThreads){
                from = (i - 1) * chunk;
                to = numbers.length;
            }else{
                from = (i - 1) * chunk;
                to = i * chunk;
            }

            tasks.add(new SubTask(numbers, from, to));
            
        }

        try {
            long total = 0L;
            List<Future<Long>> futures = pool.invokeAll(tasks);
            for (Future<Long> future : futures){
                total += future.get();
            }
            return total;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public void shutdown() {
        pool.shutdown();
    }

    class SubTask implements Callable<Long>{
        private int[] numbers;
        private int from, to;

        public SubTask(int[] numbers, int from, int to){
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        public Long call() throws Exception {
            long total = 0L;

            for (int i = from; i < to; i++) {
                total += numbers[i];
            }

            return total;
        }
    }
}
