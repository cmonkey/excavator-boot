package org.excavator.boot.experiment;

import org.excavator.boot.experiment.executeShell.OSUtils;
import org.excavator.boot.experiment.executeShell.TaskUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class OSUtilsTest {

    @Test
    @DisplayName("test os utils")
    public void testOsUtils() throws RuntimeException {
        final var es = Executors.newCachedThreadPool();
        try{
            final var r =
                    TaskUtils.withTimeout(es, Duration.ofSeconds(2),
                            () -> OSUtils.executeShellCommand(es, "ls", "-alh"));
            System.out.println(r.stdout());
            System.out.println(r.stderr());
            System.out.println(r.exitCode());
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }finally {
            es.shutdown();
        }
    }
}
