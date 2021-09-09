package org.excavator.boot.experiment;

import jdk.jfr.consumer.EventStream;

import java.io.IOException;

public class RemoteRecordingStream {
    public static void main(String[] args) {
        try(var stream = EventStream.openRepository()){
            stream.onEvent("jdk.JavaMonitorEnter", System.out::println);
            stream.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
