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
package org.excavator.boot.experiment.stream;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class ByteBufferApp {

    public static void printCurrentState(String label, Buffer buffer) {
        System.out.printf("%s - position: %d, limit: %d, capacity: %d\n", label, buffer.position(),
            buffer.limit(), buffer.capacity());
    }

    public static void main(String[] args) {
        var buffer = ByteBuffer.allocate(6);
        printCurrentState("After allocation", buffer);

        buffer.put(new byte[] { 10, 20, 30, 40 });
        printCurrentState("After put", buffer);

        buffer.flip();
        printCurrentState("After flip", buffer);

        buffer.get();
        printCurrentState("After get", buffer);

        buffer.rewind();
        printCurrentState("After rewind", buffer);

        while (buffer.hasRemaining()) {
            System.out.printf("[%d-%d] ", buffer.position(), buffer.get());
        }

        System.out.println();

        buffer.clear();
        printCurrentState("After clear", buffer);
    }
}
