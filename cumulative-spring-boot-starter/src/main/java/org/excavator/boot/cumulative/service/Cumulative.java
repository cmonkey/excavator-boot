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
package org.excavator.boot.cumulative.service;

import org.excavator.boot.cumulative.utils.DateTimeUtils;


public interface Cumulative {
    

    void countByDay(String key, Dimension... dimensions) throws IllegalAccessException;

    void countByYear(String key, Dimension... dimensions) throws IllegalAccessException;


    void countByMonth(String key, Dimension... dimensions) throws IllegalAccessException;
    

    void countByDayAndMonth(String key, Dimension... dimensions) throws IllegalAccessException;
    

    String queryByDay(String key, String dimensionKey);

    String queryByYear(String key, String dimensionKey);


    String queryByMonth(String key, String dimensionKey);
    
    default String getKey(String prefix, String key, CumulativeMode mode) {
        StringBuilder sb = new StringBuilder(prefix);
        switch (mode) {
        case DAY:
            sb.append(DateTimeUtils.getCurrentDateStr());
            break;
        case MONTH:
            sb.append(DateTimeUtils.getCurrentMonthStr());
            break;
        case YEAR:
            sb.append(DateTimeUtils.getCurrentYearStr());
            break;
        default:
            break;
        }
        sb.append(".").append(key);
        return sb.toString();
    }
    
    enum CumulativeMode {
        DAY("0"), MONTH("1"), DAY_AND_MONTH("2"),YEAR("3");
        String mode;
        private CumulativeMode(String mode) {
            this.mode = mode;
        }
        public String getMode() {
            return this.mode;
        }
    }
    
    public static class Dimension {
        private String key;
        private String value;

        public Dimension(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}


