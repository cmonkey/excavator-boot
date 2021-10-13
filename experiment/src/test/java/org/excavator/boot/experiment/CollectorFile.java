package org.excavator.boot.experiment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CollectorFile {
    public static void main(String[] args) throws IOException {
        var filename = args[0];
        var lines = Files.readAllLines(Paths.get(filename));
        var map = lines.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        var atomic = new AtomicInteger(0);
        map.forEach((k, v) -> {
            System.out.println(k+","+v);
            atomic.incrementAndGet();
        });
    }
}
