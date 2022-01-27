package org.excavator.boot.experiment;

import java.time.Clock;
import java.time.LocalDateTime;

public class ReplaceableClockDemo {

    public static void main(String[] args) {
        ReplaceableClockDemo demo  = new ReplaceableClockDemo(Clock.systemUTC());
        demo.isNowBefore(LocalDateTime.of(2017, 3, 1, 12, 0));

    }

    private final Clock clock;

    public ReplaceableClockDemo(Clock clock){
        this.clock = clock;
    }

    public boolean isNowBefore(LocalDateTime dateTime){
        return LocalDateTime.now(clock).isBefore(dateTime);
    }
}
