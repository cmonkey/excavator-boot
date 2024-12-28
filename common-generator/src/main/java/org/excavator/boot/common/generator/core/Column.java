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

import java.util.List;

public class Column {

    //列数据类型
    private String type;

    //字段名
    private String name;

    //字段名，java风格
    private String nameJ;

    //备注，注释
    private String remark;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameJ() {
        return nameJ;
    }

    public void setNameJ(String nameJ) {
        this.nameJ = nameJ;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Column() {
    }

    public Column(String type, String name, String nameJ) {
        this.type = type;
        this.name = name;
        this.nameJ = nameJ;
    }

    public Column(String type, String name, String nameJ, String remark) {
        this.type = type;
        this.name = name;
        this.nameJ = nameJ;
        this.remark = remark;
    }

    public static boolean typeContains(List<Column> columns, String type) {
        for (Column c : columns) {
            if (c.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

}
