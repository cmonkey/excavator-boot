package org.excavator.boot.experiment;

import java.util.List;

public class ProcessStrings {

    private static void processStrings(List<String> strings, boolean skipHeader) {
        var stream = strings.stream();
        if(skipHeader) {
            stream.skip(1);
        }else{
            stream.skip(0);
        }

        stream.map(String::toUpperCase).forEach(System.out::println);
    }

    public static void main(String[] args) {
        processStrings(
                List.of("streams", "are", "cool"),
                false
        );
    }
}
