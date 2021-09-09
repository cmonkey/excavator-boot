package org.excavator.boot.experiment;

import jdk.jfr.consumer.EventStream;

import java.io.IOException;
import java.nio.file.Path;

public class RemoteRecordingStream {
    public static void main(String[] args) {
        // Passive, in process
        try(var stream = EventStream.openRepository()){
            stream.onEvent("jdk.JavaMonitorEnter", System.out::println);
            stream.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Passive, out of process
        var path = Path.of("/tmp/2021-09-09_60185");
        try(var stream = EventStream.openRepository(path)){
            stream.onEvent("Jdk.JavaMonitorEnter", System.out::println);
            stream.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
