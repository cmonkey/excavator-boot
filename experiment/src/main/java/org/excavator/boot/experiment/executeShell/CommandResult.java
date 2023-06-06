package org.excavator.boot.experiment.executeShell;

public record CommandResult(
    int exitCode,
    String stdout,
    String stderr
){}