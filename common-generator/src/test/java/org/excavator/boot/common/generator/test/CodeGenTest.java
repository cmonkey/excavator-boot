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
package org.excavator.boot.common.generator.test;

import org.apache.commons.io.FileUtils;
import org.excavator.boot.common.generator.Builder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CodeGenTest {
    private final static Logger logger = LoggerFactory.getLogger(CodeGenTest.class);

    @Test
    public void testCodeGen() throws IOException {
        Builder.getInstance().codegen(System.getProperty("user.dir") + "/src/test/resources/",
            null, null, null, null);

        String user = "bsc";
        String password = "bsc";
        String database = "bsc";
        String host = "www.excavator.boot";
        int port = 3306;
        String driverClass = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                     + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String packagePath = "com.excavator.web";
        String author = "";
        String table = "";

        Builder.getInstance().codegen(user, password, host, port, database, driverClass, url,
            packagePath, table, author);

        packagePath = "com.demo";
        table = "ac_date";

        Builder.getInstance().codegen(user, password, host, port, database, driverClass, url,
            packagePath, table, author);

        Path projectPath = Paths.get("project");
        diffFileNames(projectPath);

        clearCodeGenDirectory(projectPath);

        logger.info("codegen success");
    }

    private void diffFileNames(Path path){

        List<String> fileNames = getFileNames(path);

        fileNames.forEach(f -> logger.info("f name = {}", f));

        String[] array = {"org", "com", "mappings"};

        long count = fileNames.stream().filter(f -> Arrays.asList(array).contains(f)).count();

        assertEquals(count , 3);
    }

    private List<String> getFileNames(Path path){
        List<String> list = new ArrayList<>();

        boolean isDirectory = isDirectory(path);

        if(isDirectory){
            try {
                list = Files.list(path).map(f -> f.getFileName().toString()).collect(Collectors.toList());
            } catch (IOException e) {
            }
        }

        return list;
    }

    private boolean isDirectory(Path path) {
        boolean isDirectory = Files.isDirectory(path);
        logger.info("isDirectory = {}", isDirectory);

        return isDirectory;
    }

    private void clearCodeGenDirectory(Path path) {

        boolean isDirectory = isDirectory(path);

        if (isDirectory) {
            try {
                File file = new File(path.toUri());
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                logger.error("clearCodeGenDirectory Exception = {}", e);
            }
        }

    }
}
