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
package org.excavator.boot.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtil {

    /**
     * 将微信服务器发送的Request请求中Body的XML解析为Map
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseRequestXmlToMap(HttpServletRequest request)
                                                                                      throws Exception {
        // 解析结果存储在HashMap中
        Map<String, String> resultMap;
        InputStream inputStream = request.getInputStream();
        resultMap = parseInputStreamToMap(inputStream);
        return resultMap;
    }

    /**
     * 将输入流中的XML解析为Map
     *
     * @param inputStream
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static Map<String, String> parseInputStreamToMap(InputStream inputStream)
                                                                                    throws DocumentException,
                                                                                    IOException {
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        //得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
         // 解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>(elementList.size());

        //遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        //释放资源
        inputStream.close();
        return map;
    }

    /**
     * 将String类型的XML解析为Map
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXmlStringToMap(String str) throws Exception {
        Map<String, String> resultMap;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        resultMap = parseInputStreamToMap(inputStream);
        return resultMap;
    }

}