package org.excavator.boot.benchmark;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ExecutionPlan {

    @Param({"100", "200", "300", "500", "1000"})
    private int iterations;
}
