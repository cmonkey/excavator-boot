package org.excavator.boot.experiment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class ExecutorServiceSample3 {
    public static void main(String[] args) {
        var executor = Executors.newScheduledThreadPool(2, new MyThreadFactory());

        //no return value
        CompletableFuture.runAsync(() -> {
            System.out.println("Inside Runnable " + Thread.currentThread().getName());
        }, executor);

        //with return value
        var cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("Inside Supplier " + Thread.currentThread().getName());
            return true;
        },executor);

        //accept the new Consumer object once the future is completed
        cf.thenAccept(result -> {
            if(result) {
                System.out.println("Inside thenAccept");
            }
        });

        //apply a Function object once the future is completed and returns boolean
        var applyResult = cf.thenApply(result -> {
            System.out.println("Inside thenApply");
            return false;
        });

        //shutdown executor manually
        executor.shutdown();
    }
}
