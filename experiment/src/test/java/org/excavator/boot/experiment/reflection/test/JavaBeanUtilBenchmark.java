package org.excavator.boot.experiment.reflection.test;

import io.lettuce.core.dynamic.annotation.Param;
import org.openjdk.jmh.annotations.*;

import java.beans.JavaBean;
import java.util.concurrent.TimeUnit;

@Fork(3)
@Warmup(iterations = 5, time = 3)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class JavaBeanUtilBenchmark {

    @Param({"fieldA",
    "nestedJavaBean.fieldA",
    "nestedJavaBean.nestedJavaBean.fieldA",
    "nestedJavaBean.nestedJavaBean.nestedJavaBean.fieldA"})
    String fieldName;
    JavaBean javaBean;

    @Setup
    public void setup(){
    }
}
