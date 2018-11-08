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
package org.excavator.boot.common.generator.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.excavator.boot.common.generator.config.Config;
import org.excavator.boot.common.generator.config.DatabaseConfig;
import org.excavator.boot.common.generator.jdbc.AbstractDaoSupport;
import org.excavator.boot.common.generator.util.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cmonkey
 */
public class BuildFactory {
    private final static Logger                     logger = LoggerFactory
                                                               .getLogger(BuildFactory.class);

    private static Map<String, Map<String, Object>> CACHE  = new HashMap<String, Map<String, Object>>();
    private static AbstractDaoSupport               dao;

    /**
     * 配置属性
     */
    private Configuration                           cfg    = new Configuration();

    /**
     * 这里设置模板的根目录
     */
    public void setSettingInfo(String loadingDir, DatabaseConfig databaseConfig) {
        try {
            cfg.setDirectoryForTemplateLoading(new File(loadingDir));
            dao = AbstractDaoSupport.getInstance(databaseConfig);
        } catch (IOException e) {
            logger.error("setSettingInfo Exception = {]", e);
        }
    }

    public AbstractDaoSupport getDao() {
        return dao;
    }

    /**
     * 根据模板生成文件
     * 
     */
    public void build(String templateFile, Object obj, String outFile) {
        try {
            Template t = cfg.getTemplate(templateFile);
            Writer out = new OutputStreamWriter(new FileOutputStream(outFile), "utf-8");
            t.process(obj, out);
        } catch (IOException | TemplateException e) {
            logger.error("build Exception = {} ", e);
        }
    }

    /**
     * POJO数据准备
     */
    public Map<String, Object> getParams(String className, String tableName, String packagePath,
                                         Config config) {
        if (CACHE.containsKey(tableName)) {
            Map<String, Object> map = CACHE.get(tableName);
            map.put("model_package", MyUtils.buildModelPackage(config, tableName));
            map.put("package_path", packagePath);
            return map;
        }
        // 数据准备,可以是Map,List或者是实体
        //String className = StringUtil.className(tableName.replace(config.getIgnorePrefix(), ""));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("package_path", packagePath);
        map.put("model_package", MyUtils.buildModelPackage(config, tableName));
        map.put("table_name", tableName);
        map.put("class_name", className);//StringUtil.className(tableName);

        List<Column> columns = dao.queryColumns(tableName);

        map.put("table_column", columns);
        map.put("hasDateColumn", Column.typeContains(columns, "Date"));
        map.put("project", config.getProject());
        map.put("author", config.getAuthor());
        map.put("sysDate", new Date());

        CACHE.put(tableName, map);

        return map;
    }

}
