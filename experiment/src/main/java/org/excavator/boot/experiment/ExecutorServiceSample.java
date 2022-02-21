package org.excavator.boot.experiment;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecutorServiceSample {

    public static void main(String[] args) {
        var executor = Executors.newScheduledThreadPool(2, new MyThreadFactory());
        executor.submit(() -> {
            System.out.println("Implemented Runnable Interface " + Thread.currentThread().getName());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.submit(() -> {
            System.out.println("Implemented Runnable Interface " + Thread.currentThread().getName());
        }, 1);

        executor.submit(() -> {
            System.out.println("Implemented Callable Interface " + Thread.currentThread().getName());
            return 1;
        });
        executor.shutdown();
    }
}

class MyThreadFactory implements ThreadFactory {
    static int i = 1;

    @Override
    public Thread newThread(Runnable r) {
        var t = new Thread(r, ""+i);
        i++;
        return t;
    }
}
