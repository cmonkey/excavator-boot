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

import org.excavator.boot.common.generator.config.Config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 */
public class StringUtil {

    public static String capFirst(String str) {
        String firstC = str.substring(0, 1);

        return str.replaceFirst(firstC, firstC.toUpperCase());
    }

    public static String javaStyle(String columnName) {
        String patternStr = "(_[a-z,A-Z])";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(columnName.toLowerCase());
        StringBuffer buf = new StringBuffer();
        while (matcher.find()) {
            String replaceStr = matcher.group();
            matcher.appendReplacement(buf, replaceStr.toUpperCase());
        }
        matcher.appendTail(buf);
        return buf.toString().replaceAll("_", "");
    }

    //去前缀
    public static String javaStyleOfTableName(Config config, String tableName) {
        String prefixs = config.getIgnorePrefix();
        String[] ps = prefixs.split(",");
        for (int i = 0; i < ps.length; i++) {
            if (tableName.startsWith(ps[i])) {
                tableName = tableName.replaceAll(ps[i], "");
            }
        }
        return StringUtil.javaStyle(tableName);
    }

    // 类名
    public static String className(String tableName) {
        //return capFirst(javaStyleOfTableName(tableName));
        return capitalize(toUpper(tableName));
    }

    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        } else {
            return (new StringBuilder(strLen)).append(Character.toTitleCase(str.charAt(0)))
                .append(str.substring(1)).toString();
        }
    }

    public static String uncapitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        } else {
            return (new StringBuilder(strLen)).append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1)).toString();
        }
    }

    /**
     * “_”+小写 转成大写字母
     * 
     * @param str
     * @return
     */
    private static String toUpper(String str) {
        char[] charArr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < charArr.length; i++) {
            if (charArr[i] == '_') {
                sb.append(String.valueOf(charArr[i + 1]).toUpperCase());
                i = i + 1;
            } else {
                sb.append(charArr[i]);
            }
        }
        return sb.toString();
    }
}
