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
package org.excavator.boot.common.test;

import org.excavator.boot.common.enums.ResolveEnum;
import org.excavator.boot.common.utils.GeneratePublicPrivateKey;
import org.excavator.boot.common.utils.GeneratePublicPrivateKeys;
import org.excavator.boot.common.utils.PublicPrivateKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratorPublicPrivateKeyTests {

    @Test
    @DisplayName("testGeneratorByRSA")
    public void testGenerator() {

        String algorithm = "RSA";

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys
            .generateKeys(algorithm, 2048);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys
            .getPublicPrivateKey(algorithm, generatePublicPrivateKey, ResolveEnum.BASE64);

        assertEquals(true, optionalPublicPrivateKey.isPresent());

        optionalPublicPrivateKey = GeneratePublicPrivateKeys.getPublicPrivateKey(algorithm,
            generatePublicPrivateKey, ResolveEnum.HEX);

        assertEquals(true, optionalPublicPrivateKey.isPresent());
    }

    @Test
    @DisplayName("testGeneratorByDSA")
    public void testGeneratorByDSA() {

        String algorithm = "DSA";

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys
            .generateKeys(algorithm, 1024);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys
            .getPublicPrivateKey(algorithm, generatePublicPrivateKey, ResolveEnum.BASE64);

        assertEquals(true, optionalPublicPrivateKey.isPresent());

        optionalPublicPrivateKey = GeneratePublicPrivateKeys.getPublicPrivateKey(algorithm,
            generatePublicPrivateKey, ResolveEnum.HEX);

        assertEquals(true, optionalPublicPrivateKey.isPresent());
    }

    @Test
    @DisplayName("testGeneratorByDH")
    public void testGeneratorByDH() {
        String algorithm = "DH";

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys
            .generateKeys(algorithm, 512);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys
            .getPublicPrivateKey(algorithm, generatePublicPrivateKey, ResolveEnum.BASE64);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        optionalPublicPrivateKey = GeneratePublicPrivateKeys.getPublicPrivateKey(algorithm,
            generatePublicPrivateKey, ResolveEnum.HEX);

        assertEquals(true, optionalPublicPrivateKey.isPresent());
    }

    @Test
    @DisplayName("testGetX509PublicKey")
    public void testGetX509PublicKey() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("open.cer");

        Optional<PublicKey> optionalPublicKey = GeneratePublicPrivateKeys
            .getX509PublicKey(inputStream);

        assertEquals(true, optionalPublicKey.isPresent());
    }

    @Test
    @DisplayName("tesGetPrivateKeyByPKCS12")
    public void testGetPrivateKeyByPKCS12() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("open.pfx");
        Optional<PrivateKey> optionalPrivateKey = GeneratePublicPrivateKeys.getPrivateKeyByPKCS12(
            inputStream, "1111test");

        assertEquals(true, optionalPrivateKey.isPresent());
    }
}
