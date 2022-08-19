package org.excavator.boot.experiment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WriteHelper {

    public static void write(String out_file, List<String> outLines) throws IOException {
        Files.write(Paths.get(out_file),outLines);
    }
}