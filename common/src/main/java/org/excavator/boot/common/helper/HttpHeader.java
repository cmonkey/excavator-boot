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
package org.excavator.boot.common.helper;

public enum HttpHeader {

    AUTHORIZATION("Authorization"),

    AUTHENTICATION_TYPE_BASIC("Basic"),

    X_AUTH_TOKEN("X-AUTH-TOKEN"),

    WWW_Authenticate("WWW-Authenticate"),

    X_FORWARDED_FOR("X-Forwarded-For"),

    PROXY_CLIENT_IP("Proxy-Client-IP"),

    WL_PROXY_CLIENT_IP("WL-Proxy-Client-IP"),

    HTTP_CLIENT_IP("HTTP_CLIENT_IP"),

    HTTP_X_FORWARDED_FOR("HTTP_X_FORWARDED_FOR");

    private final String key;

    HttpHeader(String key) {
        this.key = key;
    }

    public String key() {
        return this.key;
    }
}
