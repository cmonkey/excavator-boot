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

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
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
import java.util.stream.IntStream;

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
        testCipher(data);
    }

    @Test
    @DisplayName("test sm2 in cipher by long block")
    public void testSm2CipherByLongBlock(){
        StringBuilder builder = new StringBuilder();

        IntStream.range(0, 100000).forEach(i -> {
            builder.append("Abcd");
        });

        testCipher(builder.toString());
    }

    private void testCipher(String data) {
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

        Optional<byte[]> optionalEncryptBySm2 = GeneratePublicPrivateKeys.encryptBySM2(input,
            publicPrivateKey.getPublicKey());

        assertEquals(true, optionalEncryptBySm2.isPresent());

        byte[] outputEncryptBySm2 = optionalEncryptBySm2.get();

        Optional<byte[]> optionalDecryptBySm2 = GeneratePublicPrivateKeys.decryptBySM2(
            outputEncryptBySm2, publicPrivateKey.getPrivateKey());

        assertEquals(true, optionalDecryptBySm2.isPresent());

        byte[] outputDecryptBySm2 = optionalDecryptBySm2.get();

        String decSm2Str = new String(outputDecryptBySm2, StandardCharsets.UTF_8);

        assertEquals(data, decSm2Str);
    }

    @Test
    @DisplayName("test sm2 decrypt")
    public void testSm2Decrypt() {
        String encData = "04832692469562d1871a63fc2fe69ff5fc9d9a0d42c145ddf0776dbf6b6d05535fcfb6dfaef5edbf0368280eff131ce947cb8768612239368e9c12916864f77c3fb753c347864782ee0e3466d2a6fd84735a3d25ee12aa6727118ae31627c93f7fd754c083092a7a0446fc0e8b2a5a";
        String base64PrivateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgACzs7q8xNRkQNX1cNuNbZJylEW27s3+njSryRHBptE6gCgYIKoEcz1UBgi2hRANCAASSBlyRGzj0NinG2FFnHesYuxmb9qztrD5IR3Mn1oiQTgDNXV/zAQpCrdWElqMss6Cnh1+6nK6W2b0PKZgqFOtU";
        String base64PublicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEkgZckRs49DYpxthRZx3rGLsZm/as7aw+SEdzJ9aIkE4AzV1f8wEKQq3VhJajLLOgp4dfupyultm9DymYKhTrVA==";

        GeneratePublicPrivateKey generatePublicPrivateKey = new GeneratePublicPrivateKey();
        generatePublicPrivateKey.setPrivateKeyEncodeBase64String(base64PrivateKey);
        generatePublicPrivateKey.setPublicKeyEncodeBase64String(base64PublicKey);

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys
            .getPublicPrivateKey("EC", generatePublicPrivateKey, ResolveEnum.BASE64);

        assertEquals(true, optionalPublicPrivateKey.isPresent());

        PublicPrivateKey publicPrivateKey = optionalPublicPrivateKey.get();

        String data = "javascript";

        Optional<byte[]> optionalEncrypt = GeneratePublicPrivateKeys.encrypt(
            data.getBytes(StandardCharsets.UTF_8), "SM2", publicPrivateKey.getPublicKey());

        assertEquals(true, optionalEncrypt.isPresent());

        logger.info("encrypt {} by hex = {}", data, Hex.encodeHexString(optionalEncrypt.get()));
        logger.info("encrypt {} by base64 = {}", data,
            Base64.encodeBase64String(optionalEncrypt.get()));

        try {
            Optional<byte[]> optionalDecrypt = GeneratePublicPrivateKeys.decrypt(
                Hex.decodeHex(encData), "SM2", publicPrivateKey.getPrivateKey());

            assertEquals(true, optionalDecrypt.isPresent());

            assertEquals("javascript1024",
                new String(optionalDecrypt.get(), StandardCharsets.UTF_8));

            Optional<byte[]> optionalDecryptBySM2 = GeneratePublicPrivateKeys.decryptBySM2(
                Hex.decodeHex(encData), publicPrivateKey.getPrivateKey());

            assertEquals(true, optionalDecryptBySM2.isPresent());
            logger.info("decrypt = {}", new String(optionalDecrypt.get(), StandardCharsets.UTF_8));

            logger.info("decrypt By SM2 = [{}]", new String(optionalDecryptBySM2.get(),
                StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.error("testSm2Decrypt Exception = {}", e);
        }
    }

}
