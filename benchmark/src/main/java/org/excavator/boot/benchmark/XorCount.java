package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
public class XorCount {

    @Param({"256", "512", "1024", "2048", "4096"})
    int size;
    private long[] left;
    private long[] right;

    @Setup(Level.Trial)
    public void setup(){
        left = new long[size];
        right = new long[size];

        for (int i = 0; i < size; i++) {
            left[i] = ThreadLocalRandom.current().nextLong();
            right[i] = ThreadLocalRandom.current().nextLong();
        }
    }

    @Benchmark
    public int fused(){
        int count = 0;
        for (int i = 0; i < left.length & i < right.length; i++) {
            left[i] ^= right[i];
            count += Long.bitCount(left[i]);
        }

        return count;
    }

    @Benchmark
    public int fissured(){
        for (int i = 0; i < left.length & i < right.length; i++) {
            left[i] ^= right[i];
        }

        int count = 0;
        for (long l : left){
            count += Long.bitCount(l);
        }

        return count;
    }
}
