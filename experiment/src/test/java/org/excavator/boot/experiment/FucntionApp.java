package org.excavator.boot.experiment;

import java.util.function.Function;

public class FucntionApp {
    public static void main(String[] args) {
        Function<String, Integer> toNumber = Integer::parseInt;
        System.out.println("To Number: " + toNumber.apply("234"));
        Function<String,String> upperCase = String::toUpperCase;
        Function<String,String> trim = String::trim;
        Function<String, String> searchEngine = upperCase.andThen(trim);
        System.out.println("Search result: " + searchEngine.apply(" test one two  "));
    }
}
