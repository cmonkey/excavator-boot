package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, warmups = 1, jvmArgsAppend = {})
@State(Scope.Benchmark)
public class ChainingLambdaBenchmark {

    @Param({"77"})
    public static Integer value;

    public static void main(String[] args) throws RunnerException {

        Options opt =
                new OptionsBuilder()
                        .include(ChainingLambdaBenchmark.class.getSimpleName())
                        .build();
        new Runner(opt).run();
    }

    @Benchmark
    public int baseline() {
        return value;
    }

    @Benchmark
    public int depth1() {
        Level9 l9;
        Level10 l10;

        l10 = () -> value;
        l9 =  () -> l10;

        return l9.next().get();
    }

    @Benchmark
    public int depth2() {
        Level8 l8;
        Level9 l9;
        Level10 l10;

        l10 = () -> value;
        l9 =  () -> l10;
        l8 =  () -> l9;

        return l8.next().next().get();
    }

    @Benchmark
    public int depth3() {
        Level7 l7;
        Level8 l8;
        Level9 l9;
        Level10 l10;

        l10 = () -> value;
        l9 =  () -> l10;
        l8 =  () -> l9;
        l7 =  () -> l8;

        return l7.next().next().next().get();
    }

    @Benchmark
    public int depth5() {
        Level5 l5;
        Level6 l6;
        Level7 l7;
        Level8 l8;
        Level9 l9;
        Level10 l10;

        l10 = () -> value;
        l9  = () -> l10;
        l8  = () -> l9;
        l7  = () -> l8;
        l6  = () -> l7;
        l5  = () -> l6;

        return l5.next().next().next().next().next().get();
    }

    @Benchmark
    public int depth10() {
        Level0 l0;
        Level1 l1;
        Level2 l2;
        Level3 l3;
        Level4 l4;
        Level5 l5;
        Level6 l6;
        Level7 l7;
        Level8 l8;
        Level9 l9;
        Level10 l10;

        l10 = () -> value;
        l9 =  () -> l10;
        l8 =  () -> l9;
        l7 =  () -> l8;
        l6 =  () -> l7;
        l5 =  () -> l6;
        l4 =  () -> l5;
        l3 =  () -> l4;
        l2 =  () -> l3;
        l1 =  () -> l2;
        l0 =  () -> l1;

        return l0.next().next().next().next().next().next().next().next().next().next().get();
    }
}
