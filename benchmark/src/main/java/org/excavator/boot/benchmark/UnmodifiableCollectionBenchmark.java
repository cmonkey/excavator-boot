package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(8)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UnmodifiableCollectionBenchmark {

    private static final long size = 10;
    final static Set<Integer> underlyingCollection = new Random().ints(size).mapToObj(i -> i )
            .collect(Collectors.toCollection(HashSet::new));
    final static Set<Integer> unmodifiableCollection = underlyingCollection;
    @Benchmark
    public boolean isEmpty(){
        return unmodifiableCollection.isEmpty();
    }

    @Benchmark
    public void forEach(Blackhole hole){
        unmodifiableCollection.forEach(hole::consume);
    }

    @Benchmark
    public void iterator(Blackhole hole){
        for (Integer i : unmodifiableCollection) {
            hole.consume(i);
        }
    }

    @Benchmark
    public void linearAccess(Blackhole hole){
        List<Integer> list =  unmodifiableCollection();
        for (int i = 0; i < list.size(); i++) {
            hole.consume(list.get(i));
        }
    }

    private List<Integer> unmodifiableCollection() {
        return (List<Integer>)unmodifiableCollection;
    }

    @Benchmark
    public void randomAccess(Blackhole hole){
        List<Integer> list = unmodifiableCollection();

        for (int at :
                getAts()) {
            hole.consume(list.get(at));
        }
    }

    private int[] getAts() {
        return IntStream.range(0, (int)size).toArray();
    }

    public static void main(String[] args) throws RunnerException {
        Options ops = new OptionsBuilder().include(UnmodifiableCollectionBenchmark.class.getSimpleName()).forks(2)
                .warmupIterations(5).measurementIterations(5).build();

        new Runner(ops).run();
    }
}
