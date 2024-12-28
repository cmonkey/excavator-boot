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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.excavator.boot.common.generator.config.DatabaseConfig;
import org.excavator.boot.common.generator.core.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDaoSupport {

    private final static Logger logger     = LoggerFactory.getLogger(AbstractDaoSupport.class);

    protected static String     driverName = "";                                               // Load the JDBC driver
    protected static String     url        = "";                                               // a JDBC url
    protected static String     username   = "";
    protected static String     password   = "";

    public static AbstractDaoSupport getInstance(DatabaseConfig databaseConfig) {
        driverName = databaseConfig.driverClass();
        url = databaseConfig.url();
        username = databaseConfig.username();
        password = databaseConfig.password();

        if (driverName.contains("oracle")) {
            return new OracleDao();
        }
        return new MysqlDao();
    }

    public List<String> queryAllTables(String nativeSql) {
        List<String> list = new ArrayList<>();
        try {
            checkDriver();
            Connection conn = getConn();
            ResultSet rs = createQuery(conn, nativeSql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            logger.error("queryAllTables Exception = {}", e);
        }
        return list;
    }

    public abstract List<String> queryAllTables();

    public abstract List<Column> queryColumns(String tableName);

    public abstract String typesConvert(String sqlType);

    protected ResultSet createQuery(Connection conn, String sql) throws SQLException {
        return conn.createStatement().executeQuery(sql);
    }

    protected Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    protected void checkDriver() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.error("checkDriver Exception = {}", e);
        }
    }

}
