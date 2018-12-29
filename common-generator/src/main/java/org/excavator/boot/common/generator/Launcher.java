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
package org.excavator.boot.common.generator;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Launcher {
    private final static Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {

        Options options = new Options();

        options.addOption("h", false, "help");
        options.addOption("user", true, "database user (example: root)");
        options.addOption("password", true, "database password (example: root)");
        options.addOption("host", true, "database host (example: 127.0.0.1)");
        options.addOption("port", true, "database port / default port 3306 (example: 3306)");
        options.addOption("database", true, "database name (example: codegen)");
        options
            .addOption("driverClass", true,
                "database driver / default [com.mysql.cj.jdbc.Driver] (example: com.mysql.cj.jdbc.Driver)");
        options
            .addOption("packagePath", true,
                "codegen package path / default packagePath org.excavator.web (example: org.excavator.service.web)");
        options.addOption("author", true, "code author / default cmonkey (example: Baby)");
        options.addOption("table", true, "table  / default null (example: ac_date)");

        options
            .addOption(
                "url",
                false,
                "database url (example: 'jdbc:mysql://127.0.0.1:3306/codegen?useUnicode=true&characterEncoding=UTF-8')");

        CommandLineParser commandLineParser = new DefaultParser();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            if (commandLine.hasOption("h")) {
                help(options);
            }

            String user = "";
            String password = "";
            String host = "";
            int port = 3306;
            String database = "";
            String driverClass = "com.mysql.cj.jdbc.Driver";
            String url = "";
            String packagePath = "org.excavator.web";
            String author = "";
            String table = "";

            if (commandLine.hasOption("user")) {
                user = commandLine.getOptionValue("user");
            } else {
                help(options);
            }

            if (commandLine.hasOption("password")) {
                password = commandLine.getOptionValue("password");
            } else {
                help(options);
            }

            if (commandLine.hasOption("host")) {
                host = commandLine.getOptionValue("host");
            } else {
                help(options);
            }

            if (commandLine.hasOption("port")) {
                port = Integer.valueOf(commandLine.getOptionValue("port", "3306"));
            }

            if (commandLine.hasOption("database")) {
                database = commandLine.getOptionValue("database");
            } else {
                help(options);
            }

            if (commandLine.hasOption("driverClass")) {
                driverClass = commandLine.getOptionValue("driverClass", "com.mysql.jdbc.Driver");
            }

            if (commandLine.hasOption("url")) {
                url = commandLine.getOptionValue("url");
            } else {
                url = "jdbc:mysql://" + host + ":" + port + "/" + database
                      + "?useUnicode=true&characterEncoding=UTF-8";
            }

            if (commandLine.hasOption("packagePath")) {
                packagePath = commandLine.getOptionValue("packagePath");
            }

            if (commandLine.hasOption("author")) {
                author = commandLine.getOptionValue("author");
            }

            if (commandLine.hasOption("table")) {
                table = commandLine.getOptionValue("table");
            }

            Builder.getInstance().codegen(user, password, host, port, database, driverClass, url,
                packagePath, table, author);
        } catch (ParseException | IOException e) {
            logger.error("parse Exception = {}", e);
            System.exit(0);
        }

    }

    private static void help(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Main", options);

        System.exit(0);
    }
}
