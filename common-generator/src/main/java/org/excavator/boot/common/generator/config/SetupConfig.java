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
package org.excavator.boot.common.generator.config;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Data
@ToString
public class SetupConfig {

    private static SetupConfig instance;
    // project work dir
    public static final String USER_DIR       = System.getProperty("user.dir");
    public static final String RESOURCES_PATH = "/src/main/resources/";
    public static final String SEPARATOR      = File.separator;

    private String             project;
    private String             packagePath;
    private String             author         = "cmonkey";
    private String             model;

    private String             ignorePrefix;

    private DatabaseConfig     databaseConfig;
    private String             templateDir;
    private TemplateMapping[]  mappings;
    private Group[]            groups;

    public static String getUserDir() {
        return USER_DIR;
    }

    public static String getSeparator() {
        return SEPARATOR;
    }

    private static String loadJson() {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(USER_DIR + RESOURCES_PATH
                                                                  + "config-ssm.json"));
            String str;
            while ((str = in.readLine()) != null) {
                int contentIndex = str.indexOf("//"); // 处理行注释
                if (contentIndex != -1) {
                    str = str.substring(0, contentIndex);
                }
                builder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static SetupConfig getInstance() {
        if (instance == null) {
            String inputJson = loadJson();
            instance = JSON.parseObject(inputJson, SetupConfig.class);
        }
        return instance;
    }
}
