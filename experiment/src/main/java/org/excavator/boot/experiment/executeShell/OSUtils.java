package org.excavator.boot.experiment.executeShell;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class OSUtils {

    public static CommandResult executecommand(ExecutorService es, Path executable, String... args) throws IOException {
        Objects.requireNonNull(executable);
        Objects.requireNonNull(args);

        final var commandArgs = prepend(executable.toAbsolutePath().toString(), args);
        final var proc = Runtime.getRuntime().exec(commandArgs);
        Future<byte[]> stdout = null;
        Future<byte[]> stderr = null;

        try{
            stdout = es.submit(() -> proc.getInputStream().readAllBytes());
            stderr = es.submit(() -> proc.getErrorStream().readAllBytes());
            return new CommandResult(proc.waitFor(),
                    new String(stdout.get(), StandardCharsets.UTF_8),
                    new String(stderr.get(), StandardCharsets.UTF_8)
                    );
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            proc.destroy();
            TaskUtils.cancelAll(stdout, stderr);
        }
    }

    public static CommandResult executeShellCommand(ExecutorService es,
                                                    String command,
                                                    String...args) throws IOException {
        Objects.requireNonNull(command);
        Objects.requireNonNull(args);
        final String shellCommand = Arrays.stream(prepend(command, args))
                .map(StringEscapeUtils::escapeXSI)
                .collect(Collectors.joining(" "));

        return executecommand(es, Path.of("/bin/sh"), "-c", shellCommand);
    }

    private static String[] prepend(String elem, String[] array){
        final var newArray = new String[array.length+1];
        newArray[0] = elem;
        System.arraycopy(array, 0, newArray, 1, array.length);
        return newArray;
    }
}
