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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class InvokeTest {

    @Test
    @DisplayName("test invoke")
    public void testInvoke() throws NoSuchMethodException, InvocationTargetException,
                            IllegalAccessException {
        var names = new ArrayList<String>();
        var add = List.class.getMethod("add", Object.class);
        add.invoke(names, "John");
        add.invoke(names, "Anton");
        add.invoke(names, "Heinz");
        System.out.println("names = " + names);
        add.invoke(names, null);
        System.out.println("names = " + names);
        add.invoke(names, 42);
        System.out.println("names = " + names);
        for (String name : names)
            System.out.println(name);
    }
}
