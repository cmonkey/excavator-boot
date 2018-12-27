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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.excavator.boot.common.generator.config.Config;
import org.excavator.boot.common.generator.config.DatabaseConfig;
import org.excavator.boot.common.generator.config.TemplateMapping;
import org.excavator.boot.common.generator.core.BuildFactory;
import org.excavator.boot.common.generator.util.MyUtils;
import org.excavator.boot.common.generator.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代码生成工具入口
 *@version 3.0
 */
public class Builder {
    private final static Logger logger = LoggerFactory.getLogger(Builder.class);

    //待生成表集合
    private static String[]     tables = {};

    private void dbSchemeToEntity(BuildFactory factory, Config config, String table) {
        // iterator all template file
        TemplateMapping[] mappings = config.getMappings();

        List<String> tableList = Arrays.asList(tables);

        if (null == tables || tables.length == 0) {
            tableList = factory.getDao().queryAllTables();

            if(StringUtils.isNotBlank(table)){
                tableList = tableList.stream().filter(t -> t.equals(table)).collect(Collectors.toList());
            }
        }

        for (TemplateMapping m : mappings) {
            // iterator all databases tables.
            for (String tableName : tableList) {

                String packagePath = m.buildPackage(config.getProject(), config.getPackagePath(),
                    config.getModel());//MyUtils.getModelName(tableName, ".")

                String className = StringUtil.className(tableName.toLowerCase().replace(
                    config.getIgnorePrefix(), ""));

                Map<String, Object> data = factory.getParams(className, tableName, packagePath,
                    config);

                factory.build(MyUtils.getTemplatePath(config, m), data,
                    MyUtils.getOutPutPath(config, m, className));
            }
        }
    }

    private static Builder instance = new Builder();

    private Builder() {
    }

    public static Builder getInstance() {
        if (null == instance) {
            instance = new Builder();
        }

        return instance;
    }

    public void codegen(String user, String password, String host, int port, String database,
                        String driverClass, String url, String packagePath, String table, String author)
                                                                                          throws IOException {
        logger
            .info(
                "codegen param user = {}, password = {}, host  = {}, port = {}, database = {}, driverClass = {}, url = {}, packagePath = {}, table = {}, author = {}",
                user, password, host, port, database, driverClass, url, packagePath, table, author);

        Path path = Files.createTempDirectory("codegen");

        logger.info("path = {}, isDirectory = {}", path, Files.isDirectory(path));

        Files.createDirectory(Paths.get(path.toString() + File.separator + "template"));

        copyPath(path);

        DatabaseConfig databaseConfig = new DatabaseConfig();

        databaseConfig.setDriverClass(driverClass);
        databaseConfig.setPassword(password);
        databaseConfig.setUsername(user);
        databaseConfig.setUrl(url);

        if (StringUtils.isBlank(author)) {
            author = getAuthor(path);
        }

        codegen(path.toString(), databaseConfig, packagePath, table, author);

        clearPath(path);
    }

    private String getAuthor(Path path) {

        FileOwnerAttributeView fileOwnerAttributeView = Files.getFileAttributeView(path,
            FileOwnerAttributeView.class);

        String author = "cmonkey";

        try {
            UserPrincipal userPrincipal = fileOwnerAttributeView.getOwner();
            author = userPrincipal.getName();
        } catch (IOException e) {
            logger.error("getAuthor Exception = {}", e);
        }

        return author;
    }

    private void clearPath(Path path) {
        try {
            FileUtils.deleteDirectory(path.toFile());
        } catch (IOException e) {
            logger.error("clearPath Exception = {}", e);
        }
    }

    private void copyPath(Path path) {

        copyFile(path, "config-ssm.json");
        copyFile(path, "template/controller.ftl");
        copyFile(path, "template/dalMapper.ftl");
        copyFile(path, "template/dto.ftl");
        copyFile(path, "template/mybatis_xml.ftl");
        copyFile(path, "template/pojo.ftl");
        copyFile(path, "template/service_impl.ftl");
        copyFile(path, "template/service_spi.ftl");
    }

    private void copyFile(Path path, String fileName) {

        try (OutputStream outputStream = new FileOutputStream(new File(path.toFile(), fileName))) {

            ClassLoader classLoader = this.getClass().getClassLoader();

            InputStream inputStream = classLoader.getResourceAsStream(fileName);

            IOUtils.copy(inputStream, outputStream);

        } catch (IOException e) {
            logger.error("copyFile Exception = {}", e);
            System.exit(0);
        }
    }

    public void codegen(String resourcesDir, DatabaseConfig databaseConfig, String packagePath,
                        String table, String author) {
        logger.info("codegen param resourceDir = {}", resourcesDir);

        Config config = getConfig(resourcesDir);

        if (null != databaseConfig) {
            config.setDatabaseConfig(databaseConfig);
        }

        if (StringUtils.isNotBlank(packagePath)) {
            config.setPackagePath(packagePath);
        }

        if (StringUtils.isNotBlank(author)) {
            config.setAuthor(author);
        }

        BuildFactory factory = new BuildFactory();

        factory.setSettingInfo(resourcesDir, config.getDatabaseConfig());

        instance.dbSchemeToEntity(factory, config, table);
    }

    private Config getConfig(String resourcesDir) {
        String json = readConfig(resourcesDir);

        return JSON.parseObject(json, Config.class);
    }

    private String readConfig(String resourcesDir) {
        StringBuilder builder = new StringBuilder("");
        try (BufferedReader in = new BufferedReader(new FileReader(resourcesDir + File.separator
                                                                   + "config-ssm.json"))) {
            String str = "";
            while ((str = in.readLine()) != null) {
                int contentIndex = str.indexOf("//"); // 处理行注释
                if (contentIndex != -1) {
                    str = str.substring(0, contentIndex);
                }
                builder.append(str);
            }
        } catch (IOException e) {
            logger.error("readConfig Exception = {}", e);

        }
        return builder.toString();
    }

}
