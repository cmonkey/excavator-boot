## Mode 

Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用”。

AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”。

SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”

SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。

FirstBenchmark 中通过BenchmarkMode 指定了当前测试使用平均时间作为度量


## Iteration 

Iteration 是 JMH 进行测试的最小单位。在大部分模式下，一次 iteration 代表的是一秒，JMH 会在这一秒内不断调用需要 benchmark 的方法，然后根据模式对其采样，计算吞吐量，计算平均执行时间等。


## Warmup 

Warmup 是指在实际进行 benchmark 前先进行预热的行为。为什么需要预热？因为 JVM 的 JIT 机制的存在，如果某个函数被调用多次之后，JVM 会尝试将其编译成为机器码从而提高执行速度。所以为了让 benchmark 的结果更加接近真实情况就需要进行预热。


## 注释

### @Benchmark

表示该方法是需要进行 benchmark 的对象，用法和 JUnit 的 @Test 类似。

### @Mode

Mode 如之前所说，表示 JMH 进行 Benchmark 时所使用的模式。

### @State

State 用于声明某个类是一个“状态”，然后接受一个 Scope 参数用来表示该状态的共享范围。因为很多 benchmark 会需要一些表示状态的类，JMH 允许你把这些类以依赖注入的方式注入到 benchmark 函数里。Scope 主要分为两种。

Thread: 该状态为每个线程独享。
Benchmark: 该状态在所有线程间共享。
关于State的用法，官方的 code sample 里有比较好的例子。

### @OutputTimeUnit

benchmark 结果所使用的时间单位。
