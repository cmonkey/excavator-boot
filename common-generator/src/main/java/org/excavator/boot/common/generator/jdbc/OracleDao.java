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
 * @author 
 */
public class OracleDao extends AbstractDaoSupport {
    private final static Logger logger = LoggerFactory.getLogger(OracleDao.class);

    @Override
    public List<String> queryAllTables() {
        return queryAllTables("select lower(tname) from tab where tabtype = 'TABLE' order by 1");

    }

    @Override
    public List<Column> queryColumns(String tableName) {
        List<Column> list = new ArrayList<>();
        try {
            checkDriver();
            Connection conn = getConn();
            String sql = "select  lower(t1.column_name), lower(t1.data_type),  t2.comments "
                         + " from  user_col_comments  t2  left  join  user_tab_columns  t1 "
                         + " on  t1.table_name  =  t2.table_name  and  t1.column_name  =  t2.column_name "
                         + " where  t1.table_name  =  upper('" + tableName + "')";
            ResultSet rs = createQuary(conn, sql);
            while (rs.next()) {
                String type = typesConvert(rs.getString(2));
                String javaStyle = StringUtil.javaStyle(rs.getString(1));
                list.add(new Column(type, rs.getString(1), javaStyle, rs.getString(3)));
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            logger.error("queryColumns Exception = {}", e);
        }
        return list;
    }

    @Override
    public String typesConvert(String oracleType) {
        if (oracleType.startsWith("varchar") || oracleType.startsWith("char")) {
            return "String";
        } else if (oracleType.startsWith("long")) {
            return "Integer";
        } else if (oracleType.startsWith("number")) {
            return "Double";
        } else if (oracleType.startsWith("date")) {
            return "Date";
        }
        return oracleType;
    }
}
