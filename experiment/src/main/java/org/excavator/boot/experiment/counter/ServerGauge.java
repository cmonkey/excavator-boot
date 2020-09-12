package org.excavator.boot.experiment.counter;

public abstract class ServerGauge extends Gauge{
    public abstract GaugeValue getAndReset();
}
