package org.excavator.boot.experiment.jfr;

import jdk.jfr.consumer.RecordingStream;

import java.time.Duration;

public class JFRStreamingRecord {

    public static void recodingStream(){
        try(var rs = new RecordingStream()){
            rs.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(1));
            rs.enable("jdk.JavaMonitorEnter").withThreshold(Duration.ofMillis(10));
            rs.onEvent("jdk.CPULoad", recordedEvent -> {
                System.out.println(recordedEvent.getFloat("machineTotal"));
            });
            rs.onEvent("jdk.JavaMonitorEnter", recordedEvent -> {
                System.out.println(recordedEvent.getClass("monitorClass"));
            });

            rs.start();
        }
    }
}
