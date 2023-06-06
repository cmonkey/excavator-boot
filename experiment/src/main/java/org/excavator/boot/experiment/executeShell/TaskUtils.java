package org.excavator.boot.experiment.executeShell;

import java.time.Duration;
import java.util.concurrent.*;

public class TaskUtils {
    public static <A> A withTimeout(ExecutorService es,
                                    Duration timeout,
                                    Callable<A> task)throws InterruptedException, TimeoutException{
        final var ft = new FutureTask<>(task);
        try{
           es.submit(ft) ;
           return ft.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            ft.cancel(true);
            throw new RuntimeException(e);
        } catch (Exception e) {
            ft.cancel(true);
            throw e;
        }
    }

    public static void cancelAll(Future<?>... futures){
        for(final var f : futures){
            if(f != null){
                f.cancel(true);
            }
        }
    }
}
