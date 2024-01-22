package org.excavator.boot.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SpawnWorker {

    private static void spawnWorker() throws IOException {
        var info = ProcessHandle.current().info();
        ArrayList<String> workerCommand = new ArrayList<>();
        info.command().ifPresent(workerCommand::add);
        info.arguments().ifPresent(args -> workerCommand.addAll(Arrays.asList(args)));
        workerCommand.add("--worker");

        new ProcessBuilder()
                .command(workerCommand)
                .inheritIO()
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .start()
                .getInputStream()
                .transferTo(System.out);
    }

    public static void main(String[] args) {
        try {
            spawnWorker();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
