package org.excavator.boot.experiment.counter;

import lombok.SneakyThrows;

import java.util.ArrayList;

public class CompoundCounter extends ServerCounter{
    private ArrayList<ServerCounter> views = new ArrayList<>();
    private Class<? extends ServerCounter> clazz;
    private long localValue = 0;

    public CompoundCounter(Class<? extends ServerCounter> clazz) {
        this.clazz = clazz;
    }

    @SneakyThrows
    @Override
    public synchronized Counter getThreadLocalView() {
        ServerCounter view = clazz.newInstance();
        views.add(view);
        return view;
    }

    @Override
    public synchronized long getAndReset() {
        long sum = localValue;
        localValue = 0;
        for (ServerCounter view :
                views) {
            sum += view.getAndReset();
        }

        return sum;

    }

    @Override
    public synchronized void add(long increment) {
        localValue += increment;
    }
}
