package org.excavator.boot.experiment;

import java.time.*;
import java.util.Arrays;

public class DateApp {
    public static void main(String[] args) {
        System.out.println("LocalDateTime: " + LocalDateTime.now());
        System.out.println("LocalDate: " + LocalDate.now());
        System.out.println("LocalDAteTime: " + LocalDateTime.now());
        System.out.println("YearMonth: " + YearMonth.now());
        System.out.println("Year: " + Year.now());
        System.out.println("Days of weeks: " + Arrays.toString(DayOfWeek.values()));
        System.out.println("Months: " + Arrays.toString(Month.values()));
    }
}
