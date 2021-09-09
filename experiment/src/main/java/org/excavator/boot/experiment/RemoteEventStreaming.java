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

import jdk.management.jfr.RemoteRecordingStream;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.time.Duration;

public class RemoteEventStreaming {
    public static void main(String[] args) throws IOException {
        var connection = getMBeanServerConnection("myhost:1099");
        var stream = new RemoteRecordingStream(connection);
        stream.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(1));
        stream.onEvent("jdk.CPULoad", event -> {
            System.out.println("Total       : " + event.getDouble("machineTotal"));
            System.out.println("JVM System  : " + event.getDouble("jvmSystem"));
            System.out.println("JVM User    : " + event.getDouble("jvmUser"));
        });
        stream.startAsync();
    }

    private static MBeanServerConnection getMBeanServerConnection(String url) throws IOException {
        var u = new JMXServiceURL(url);
        var c = JMXConnectorFactory.connect(u);
        var connection = c.getMBeanServerConnection();
        return connection;
    }
}
