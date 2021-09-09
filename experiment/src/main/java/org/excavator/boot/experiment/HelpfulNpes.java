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

public class HelpfulNpes {

    public static void main(String[] args) {

        var countryName = new Customer().address.country.name; // cannot read field "country" because "address" is null
                                                               // 无法读取字段country, 因为address 是空
        System.out.println(countryName);
    }

    public static class Customer {
        public Address address;
    }

    public static class Address {
        public Country country;
    }

    public static class Country {
        public String name;
    }
}
