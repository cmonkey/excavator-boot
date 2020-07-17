package org.excavator.boot.base.test;

import org.excavator.boot.base.forkjoin.ForkJoinCountTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static org.junit.jupiter.api.Assertions.*;

class ForkJoinCountTaskTest {

    @Test
    @DisplayName("testForkJoinCountTask")
    public void testForkJoinCountTask(){
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCountTask task = new ForkJoinCountTask(1, 100);
        ForkJoinTask<Integer> result = pool.submit(task);
        assertEquals(5050, result);
    }

}