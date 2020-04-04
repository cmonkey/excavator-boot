package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import javax.management.MXBean;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class UnmodifiableCollectionBenchmark {

    private long size = 10;
    Set<Integer> underlyingCollection = new Random().ints(size).mapToObj(i -> i )
            .collect(Collectors.toCollection(HashSet::new));
    Set<Integer> unmodifiableCollection = underlyingCollection;
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
}
