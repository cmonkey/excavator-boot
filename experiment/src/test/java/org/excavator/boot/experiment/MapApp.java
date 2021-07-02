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
package org.excavator.boot.experiment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MapApp {
    public static void main(String[] args) {
        Map<String,String> medias = new HashMap<>();
        medias.put("facebook", "otaviojava");
        medias.put("twitter", "otaviojava");
        medias.put("linkedin", "otaviojava");
        System.out.println("The medias values " + medias);
        medias.forEach((k, v) -> System.out.println("the key = " + k + " the value = " + v));
        medias.compute("twitter", (k, v) -> k + "-" +v);
        System.out.println("the medias values " + medias);
        medias.computeIfAbsent("social", k -> "no media found: " + k);
        medias.computeIfPresent("social", (k,v) -> k + " " + v);
        System.out.println("the medias values " + medias);
        medias.replaceAll((k, v) -> v.toUpperCase(Locale.CHINA));
        System.out.println("the medias values " + medias);
    }
}
