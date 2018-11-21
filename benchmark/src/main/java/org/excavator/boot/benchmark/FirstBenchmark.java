package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class FirstBenchmark {

    @Benchmark
    public int sleepAWhile(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(FirstBenchmark.class.getSimpleName()) // 测试类的名字，可以通过正则表达式进行匹配
                .forks(1) // fork 次数，fork 多少个进程进行测试
                .warmupIterations(5) // 预热的迭代次数
                .measurementIterations(5) // 实际测量的次数
                .build();
        new Runner(opts).run();
    }
}
