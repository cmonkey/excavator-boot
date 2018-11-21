package org.excavator.boot.benchmark;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;

public class BenchmarkRunner {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void init(){
        System.out.println("benchmark init");
    }

    @Benchmark
    @Fork(value = 1, warmups = 2)
    @BenchmarkMode(Mode.Throughput)
    public void throughput(){
        System.out.println("benchmark throughout");
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Main.main(args);
    }
}
