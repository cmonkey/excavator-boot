package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
}
