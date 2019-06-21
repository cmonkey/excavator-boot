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

import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.excavator.boot.common.enums.ResolveEnum;
import org.excavator.boot.common.utils.GeneratePublicPrivateKey;
import org.excavator.boot.common.utils.GeneratePublicPrivateKeys;
import org.excavator.boot.common.utils.PublicPrivateKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SM2AsymmetricAlgorithmTests {
    private final static Logger logger = LoggerFactory.getLogger(SM2AsymmetricAlgorithmTests.class);

    @Test
    @DisplayName("test key pair generation")
    public void testKeyPairGeneration() {

        String algorithm = "EC";

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys
            .generateKeysByEC(algorithm);

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
    @DisplayName("test signature by sm2")
    public void testSignature() {
        String data = "Hello World";
        byte[] input = data.getBytes(StandardCharsets.UTF_8);

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys
            .generateKeysByEC("EC");

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        logger.info("generatePublicPrivateKey = {}", generatePublicPrivateKey);

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys
            .getPublicPrivateKey("EC", generatePublicPrivateKey, ResolveEnum.BASE64);

        assertEquals(true, optionalPublicPrivateKey.isPresent());

        PublicPrivateKey publicPrivateKey = optionalPublicPrivateKey.get();

        // Generate SM2sign with sm3 signature verification algorithm instance

        Optional<byte[]> optionalSign = GeneratePublicPrivateKeys.sign(input,
            GMObjectIdentifiers.sm2sign_with_sm3.toString(), publicPrivateKey.getPrivateKey());

        assertEquals(true, optionalSign.isPresent());

        byte[] signbytes = optionalSign.get();

        Optional<Boolean> optionalVerifySign = GeneratePublicPrivateKeys
            .verifySign(signbytes, input, GMObjectIdentifiers.sm2sign_with_sm3.toString(),
                publicPrivateKey.getPublicKey());

        assertEquals(true, optionalVerifySign.isPresent());

        assertEquals(true, optionalVerifySign.get());
    }

    @Test
    @DisplayName("test sm2 in cipher")
    public void testCipher() {
        String data = "Hello World";
        byte[] input = data.getBytes(StandardCharsets.UTF_8);

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys
            .generateKeysByEC("EC");

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys
            .getPublicPrivateKey("EC", generatePublicPrivateKey, ResolveEnum.BASE64);

        assertEquals(true, optionalPublicPrivateKey.isPresent());

        PublicPrivateKey publicPrivateKey = optionalPublicPrivateKey.get();

        Optional<byte[]> optionalEncrypt = GeneratePublicPrivateKeys.encrypt(input, "SM2",
            publicPrivateKey.getPublicKey());

        assertEquals(true, optionalEncrypt.isPresent());

        byte[] outputEncrypt = optionalEncrypt.get();

        Optional<byte[]> optionalDecrypt = GeneratePublicPrivateKeys.decrypt(outputEncrypt, "SM2",
            publicPrivateKey.getPrivateKey());

        assertEquals(true, optionalDecrypt.isPresent());

        byte[] outputDecrypt = optionalDecrypt.get();

        String decStr = new String(outputDecrypt, StandardCharsets.UTF_8);

        assertEquals(data, decStr);
    }

}
