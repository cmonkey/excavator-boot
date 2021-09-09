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
