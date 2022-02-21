package org.excavator.boot.experiment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ExecutorServiceSample2 {
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

        //get blocks the thread and waits for the future to complete
        try{
            System.out.println("Result from suppler " + cf.get());
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //shutdown executor manually
        executor.shutdown();
    }
}
