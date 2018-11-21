package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class SecondBenchmark {

    @Param({"10000", "100000", "1000000"})
    private int length;
    private int[] numbers;

    private Calculator singelThreadCalculator;
    private Calculator multiThreadCalculator;

    @Setup
    public void prepare(){
        numbers = IntStream.rangeClosed(1,length).toArray();
        singelThreadCalculator = new SinglethreadCalculator();
        multiThreadCalculator = new MultithreadCalculator(Runtime.getRuntime().availableProcessors());
    }

    @TearDown
    public void shutdown(){
        singelThreadCalculator.shutdown();
        multiThreadCalculator.shutdown();
    }

    @Benchmark
    public long singleThreadCalculator(){
        return singelThreadCalculator.sum(numbers);
    }

    @Benchmark
    public long multiThreadCalculator(){
        return multiThreadCalculator.sum(numbers);
    }

    public static void main(String[] args) throws RunnerException {
        Options  ops = new OptionsBuilder()
                .include(SecondBenchmark.class.getSimpleName())
                .forks(2)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(ops).run();
    }
}
