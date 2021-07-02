package org.excavator.boot.experiment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SortApp {
    public static void main(String[] args) {
        Set<String> fruits = new HashSet<>(List.of("Bananas", "Melon", "Watermelon"));
        fruits.forEach(System.out::println);
        fruits.removeIf("Bananas"::equals);
        System.out.println("After sort: ");
        fruits.forEach(System.out::println);
    }
}
