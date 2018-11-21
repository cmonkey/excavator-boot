package org.excavator.boot.benchmark;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

@State(Scope.Thread)
public class BenchmarkStream {

    public static int[] ints;

    @Setup
    public void setup(){
        ints = new int[1000_000_00];

        Random random = new Random();

        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt();
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    public static void forLoop(){

        int[] a = ints;
        int e = a.length;
        int m = Integer.MIN_VALUE;

        for (int i = 0; i < e; i++) {
            if(a[i] > m){
                m = a[i];
            }
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    public static void stream(){
        final int reduce = IntStream.of(ints).reduce(Integer.MIN_VALUE, Math::max);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Main.main(args);
    }
}
