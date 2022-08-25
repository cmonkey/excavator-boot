package org.excavator.boot.experiment;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class StreamPeekTest {

    @Test
    @DisplayName("test stream peek")
    public void testStreamPeek(){
        var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<String>> futureList = Lists.newArrayList();

        IntStream.range(0, 10).forEach(i -> {
            var f = executor.submit(() -> "Hi and i is " + i + " Thread name is " + Thread.currentThread().getName());
            futureList.add(f);
        });

        var isAllDone = futureList.stream().peek(f -> {
            try {
                var r = f.get();
                System.out.println(r);
            }catch (Exception e){
            }
        }).allMatch(Future::isDone);
        System.out.println(isAllDone);
    }
}
