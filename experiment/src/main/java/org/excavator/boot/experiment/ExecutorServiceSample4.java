package org.excavator.boot.experiment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ExecutorServiceSample4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newScheduledThreadPool(2, new MyThreadFactory());
        var cf = CompletableFuture.supplyAsync(() -> 1,executor);
        //result of cf is passed to another future object that invokes add method
        var result = cf.thenCompose(x -> CompletableFuture.supplyAsync(() -> add(x, 2), executor));
        System.out.println("Result " + result.get());

        //shutdown executor manually
        executor.shutdown();
    }

    public static int add(int x, int y) {
        return x + y;
    }
}
