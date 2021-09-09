package org.excavator.boot.experiment;

import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingStream;
import jdk.management.jfr.RemoteRecordingStream;

import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.nio.file.Path;

public class RemoteRecordingStreamExample {
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
            stream.onEvent("jdk.JavaMonitorEnter", System.out::println);
            stream.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Active, in process
        try(var stream = new RecordingStream()){
            stream.enable("jdk.JavaMonitorEnter").withStackTrace();
            stream.onEvent("jdk.JavaMonitorEnter", System.out::println);
            stream.start();
        }

        // Active, out of process (and over the network)

        remoteRecordStream();
    }

    private static void remoteRecordStream(){
        // Active, out of process (and over the network)
        var host = "com.example";
        var port = 7091;
        var url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";

        try {
            var u = new JMXServiceURL(url);
            var c = JMXConnectorFactory.connect(u);
            var connection = c.getMBeanServerConnection();

            try (var stream = new RemoteRecordingStream(connection)) {
                stream.enable("jdk.JavaMonitorEnter").withStackTrace();
                stream.onEvent("jdk.JavaMonitorEnter", System.out::println);
                stream.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
