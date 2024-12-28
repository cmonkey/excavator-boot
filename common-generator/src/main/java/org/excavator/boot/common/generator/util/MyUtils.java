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
package org.excavator.boot.common.generator.util;

import java.io.File;

import org.excavator.boot.common.generator.config.Config;
import org.excavator.boot.common.generator.config.Group;
import org.excavator.boot.common.generator.config.SetupConfig;
import org.excavator.boot.common.generator.config.TemplateMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cmonkey
 */
public class MyUtils {
    private final static Logger logger = LoggerFactory.getLogger(MyUtils.class);

    public static String getTemplatePath(Config config, TemplateMapping m) {
        return config.templateDir() + File.separator + m.template();
    }

    public static String getGroupName(Config config, String tableName) {
        Group[] groups = config.groups();
        String name;
        for (Group g : groups) {
            name = g.findGroupName(tableName);
            if (name != null) {
                return name;
            }
        }
        return null;
    }

    public static String getModelName(Config config, String tableName, String separator) {
        String g = getGroupName(config, tableName);
        if (g == null) {
            return StringUtil.javaStyleOfTableName(config, tableName);
        }

        logger.info("组名不能为空 = {}", g);

        return g;
    }

    public static String getOutPutPath(Config config, TemplateMapping m, String tableName) {

        String path = m.buildDir(config.project(), config.packagePath(), config.model())
                      + SetupConfig.SEPARATOR;//getModelName(tableName, "/")

        path += m.sPadding() + StringUtil.className(tableName) + m.ePadding() + "." + m.suffix();

        logger.info("getOutPutPath ### modelName = {}", getModelName(config, tableName, "/"));
        logger.info("getOutPutPath path = {}", path);

        mkdir(path);

        return path;
    }

    public static void mkdir(String filePath) {
        int index = filePath.lastIndexOf("\\");
        int index2 = filePath.lastIndexOf("/");
        if (index + index2 == -2) {
            return;
        }
        index = index > index2 ? index : index2;
        if (index != -1 && !new File(filePath.substring(0, index)).exists()) {

            logger.info("mkdir = {}", filePath.substring(0, index));

            new File(filePath.substring(0, index)).mkdirs();
        }
    }

    public static String buildModelPackage(Config config, String tableName) {
        return config.packagePath() + "." + getModelName(config, tableName, ".");
    }

}
