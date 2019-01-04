package org.excavator.boot.jdk;

import static org.excavator.boot.jdk.ExampleWithNewSwitch.Month.DECEMBER;

public class ExampleWithNewSwitch{
    enum Month{
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    }

    static boolean isWinter(Month month){
        return switch(month){
            case NOVEMBER, DECEMBER, JANUARY -> true;
            default -> false;
        };
    }

    public static void main(String[] args) {
        System.out.printf("%s is winter: %b%n", Month.DECEMBER, isWinter(Month.DECEMBER));
        System.out.printf("%s is winter: %b%n", Month.AUGUST, isWinter(Month.AUGUST));
        System.out.printf("%s is winter: %b%n", Month.NOVEMBER, isWinter(Month.NOVEMBER));
        System.out.printf("%s is winter: %b%n", Month.JUNE, isWinter(Month.JUNE));
    }
}

