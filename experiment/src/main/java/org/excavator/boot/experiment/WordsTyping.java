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

import static java.lang.System.*;

public class WordsTyping {

    public static void main(String[] args) {
    }

    public int wordsTyping(String[] sentence, int rows, int cols) {
        var s = String.join(" ", sentence) + " ";
        var len = s.length();
        var count = 0;
        var map = new int[len];

        for (int i = 1; i < len; ++i) {
            map[i] = s.charAt(i) == ' ' ? 1 : map[i - 1] - 1;
        }

        for (int i = 0; i < rows; ++i) {
            count += cols;
            count += map[count % len];
        }

        return count / len;
    }

}
