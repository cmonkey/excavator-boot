/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.experiment.jfr;

import jdk.jfr.Configuration;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingStream;
import lombok.SneakyThrows;
import lombok.val;

import javax.management.MBeanConstructorInfo;
import javax.management.MBeanServerConnection;
import java.time.Duration;

public class JFRStreamingRecord {

    @SneakyThrows
    public static void recodingStream(Duration duration){
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
            rs.awaitTermination(duration);
        }
    }

    @SneakyThrows
    public static void recordingConfiguration(Duration duration){
        var configuration = Configuration.getConfiguration("default");
        try(var rs = new RecordingStream(configuration)){
            rs.onEvent("jdk.GarbageCollection", System.out::println);
            rs.onEvent("jdk.CPULoad", System.out::println);
            rs.onEvent("jdk.JVMInformation", System.out::println);
            rs.start();
            rs.awaitTermination(duration);
        }
    }

    @SneakyThrows
    public static void openRepository(Duration duration){
        try(var es = EventStream.openRepository()){
            es.onEvent("jdk.CPULoad", recordedEvent -> {
                System.out.println("CPU Load " + recordedEvent.getEndTime());
                System.out.println(" Machine total: " + 100 * recordedEvent.getFloat("machineTotal") + "%");
                System.out.println(" Jvm User: " + 100 * recordedEvent.getFloat("jvmUser") + "%");
                System.out.println(" Jvm System: " + 100 * recordedEvent.getFloat("jvmSystem") + "%");;
                System.out.println();
            });
            es.onEvent("jdk.GarbageCollection", recordedEvent -> {
                System.out.println("Garbage collection: " + recordedEvent.getLong("gcId"));
                System.out.println(" Cause: " + recordedEvent.getString("cause"));
                System.out.println(" Total pause: " + recordedEvent.getDuration("sumOfPause"));
                System.out.println(" Longest pause: " + recordedEvent.getDuration("longestPause"));
                System.out.println();
            });

            es.startAsync();
            es.awaitTermination(duration);
        }
    }

    public static void recordingByJMS(){
        var host = "127.0.0.1";
        var port = 9999;
        //FIXME future api call remoteRecordingStream
        /*
        var con = connect(host, port);

        try(var es = new RemoteRecordingStream(conn)){

        }
         */
    }

}
