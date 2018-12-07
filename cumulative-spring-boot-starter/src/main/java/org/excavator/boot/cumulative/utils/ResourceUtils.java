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
package org.excavator.boot.cumulative.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceUtils {
    public static final String ENTER_NEW_LINE             = System.getProperty("line.separator");
    public static final String JAR_PACKAGE_PATH_SEPARATOR = "/";

    public ResourceUtils() {
    }

    public static InputStream getResourceAsStream(String path) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    }

    public static String getFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(filePath)));

        while (true) {
            String str = null;
            if ((str = br.readLine()) == null) {
                return content.toString();
            }

            content.append(str).append(ENTER_NEW_LINE);
        }
    }
}