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

import org.excavator.boot.common.generator.Builder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CodeGenTest {
    private final static Logger logger = LoggerFactory.getLogger(CodeGenTest.class);

    @Test
    public void testCodeGen() throws IOException {
        Builder.getInstance().codegen(System.getProperty("user.dir") + "/src/test/resources/",
            null, null, null);

        String user = "root";
        String password = "root";
        String database = "boot";
        String host = "www.excavator.boot";
        int port = 3306;
        String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                     + "?useUnicode=true&amp;characterEncoding=UTF-8";
        String packagePath = "com.excavator.web";
        String author = "";

        Builder.getInstance().codegen(user, password, host, port, database, driverClass, url,
            packagePath, author);

        packagePath = "com.demo";

        Builder.getInstance().codegen(user, password, host, port, database, driverClass, url,
            packagePath, author);

        logger.info("codegen success");
    }
}
