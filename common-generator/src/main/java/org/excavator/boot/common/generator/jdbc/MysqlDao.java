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
package org.excavator.boot.common.generator.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.excavator.boot.common.generator.core.Column;
import org.excavator.boot.common.generator.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cmonkey
 */
public class MysqlDao extends AbstractDaoSupport {

    private final static Logger logger = LoggerFactory.getLogger(MysqlDao.class);

    @Override
    public List<String> queryAllTables() {
        return queryAllTables("show tables");
    }

    @Override
    public List<Column> queryColumns(String tableName) {
        List<Column> list = new ArrayList<>();
        try {
            checkDriver();
            Connection conn = getConn();
            ResultSet rs = createQuary(conn, "show full fields from " + tableName);
            while (rs.next()) {
                String type = typesConvert(rs.getString(2));
                String javaStyle = StringUtil.javaStyle(rs.getString(1));
                list.add(new Column(type, rs.getString(1), javaStyle, rs.getString(9)));
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            logger.error("queryColumns Exception = {}", e);
        }
        return list;
    }

    /**
     * 功能：获得列的数据类型
     * 
     */
    @Override
    public String typesConvert(String sqlType) {
        sqlType = sqlType.substring(0,
            !sqlType.contains("(") ? sqlType.length() : sqlType.indexOf("("));
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                   || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                   || sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("double")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                   || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                   || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }
        return "String";
    }

    //@Override
    @Deprecated
    public String typesConverts(String mysqlType) {
        mysqlType = mysqlType.substring(0, !mysqlType.contains("(") ? mysqlType.length()
            : mysqlType.indexOf("("));

        if (mysqlType.equalsIgnoreCase("varchar") || mysqlType.equalsIgnoreCase("char")
            || mysqlType.equalsIgnoreCase("nvarchar") || mysqlType.equalsIgnoreCase("nchar")
            || mysqlType.equalsIgnoreCase("text") || mysqlType.startsWith("longtext")) {
            return "String";
        } else if (mysqlType.startsWith("int")) {
            return "int";
        } else if (mysqlType.startsWith("bigint")) {
            return "long";
        } else if (mysqlType.startsWith("double")) {
            return "double";
        } else if (mysqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (mysqlType.startsWith("tinyint")) {
            return "byte";
        } else if (mysqlType.equalsIgnoreCase("datetime") || mysqlType.equalsIgnoreCase("date")
                   || mysqlType.startsWith("timestamp")) {
            return "Date";
        } else if (mysqlType.equalsIgnoreCase("decimal") || mysqlType.equalsIgnoreCase("numeric")
                   || mysqlType.equalsIgnoreCase("real") || mysqlType.equalsIgnoreCase("money")
                   || mysqlType.equalsIgnoreCase("smallmoney")
                   || mysqlType.equalsIgnoreCase("double")) {
            return "double";
        }
        return mysqlType;
    }
}
