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

import java.io.FileNotFoundException;

public class ExceptionChaosChallenge {
    static String s = "-";

    public static void main(String[] args) {
        try {
            throw new IllegalArgumentException();
        } catch (Exception e) {
            try {
                try {
                    throw new FileNotFoundException();
                } catch (RuntimeException ex) {
                    s += "run";
                }
            } catch (Exception x) {
                s += "exc";
            } finally {
                s += "fin";
            }
        } finally {
            s += "of";
            try {
                throw new UnknownError("JVMError");
            } catch (Error error) {
                s += "Error" + error.getMessage();
            }
        }

        System.out.println(s);
    }
}
