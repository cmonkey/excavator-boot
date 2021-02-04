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
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class CharBufferApp {

    public static void printDefaults() {
        System.out.printf("Default CharacterSet: %s%n", Charset.defaultCharset().name());
        System.out.printf("Character Bytes: %s%n", Character.BYTES);
    }

    public static void printCurrentState(String label, Buffer buffer) {
        System.out.printf("%s - position: %d, limit: %d, capacity: %d%n", label, buffer.position(),
            buffer.limit(), buffer.capacity());
    }

    public static String byteToBinaryString(byte b) {
        return Integer.toBinaryString(b & 0xFF);
    }

    public static void charBufferToByteBuffer(CharSequence words, Charset charset) {
        System.out.printf("%nCharSequence: %s, Charset: %s%n", words, charset);

        var charBuffer = CharBuffer.wrap(words);
        printCurrentState("CharBuffer", charBuffer);

        while (charBuffer.hasRemaining()) {
            System.out.printf("[%d: %s]", charBuffer.position(), charBuffer.get());
        }

        System.out.println();

        charBuffer.rewind();

        var byteBuffer = charset.encode(charBuffer);
        printCurrentState("ByteBuffer", byteBuffer);
        while (byteBuffer.hasRemaining()) {
            System.out.printf("[%d: %s] %n", byteBuffer.position(),
                byteToBinaryString(byteBuffer.get()));
        }
    }

    public static void main(String[] args) {
        printDefaults();
        charBufferToByteBuffer("Hello", Charset.defaultCharset());
        charBufferToByteBuffer("வணக்கம்", Charset.defaultCharset());
    }
}
