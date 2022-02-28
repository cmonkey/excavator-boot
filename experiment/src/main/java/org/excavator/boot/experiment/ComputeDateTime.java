package org.excavator.boot.experiment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ComputeDateTime {

    public static void main(String[] args) {
        var now = System.currentTimeMillis();
        System.out.println("current: " + now);

        var dateTime = LocalDateTime.now().withMonth(2).withDayOfMonth(15).withHour(20).withMinute(0).withSecond(0);
        var zdt = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        System.out.println("millis: " + zdt.toInstant().toEpochMilli());
    }
}
