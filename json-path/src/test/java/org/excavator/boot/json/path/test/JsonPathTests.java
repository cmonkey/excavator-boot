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
package org.excavator.boot.json.path.test;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonPathTests {

    static String json = "";

    @BeforeAll
    private static void initJsonText() throws URISyntaxException, IOException {
        URI uri = Objects.requireNonNull(
            JsonPathTests.class.getClassLoader().getResource("simple.json")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri));

        json = String.join("", lines);
    }

    @Test
    public void shouldMatchCountOfObjects() {

        Map<String, String> objectMap = JsonPath.read(json, "$");

        assertEquals(3, objectMap.keySet().size());
    }

    @Test
    public void shouldMatchCountOfArrays() {
        JSONArray jsonArray = JsonPath.read(json, "$.items.book[*]");
        assertEquals(2, jsonArray.size());
    }
}
