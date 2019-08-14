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

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

public class GenerateSymmetricencryption {
    private static final Logger logger = LoggerFactory.getLogger(GenerateSymmetricencryption.class);

    public static Optional<SecretKey> getSecretKey(String algorithm, int numBits) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm,
                new BouncyCastleProvider());
            keyGenerator.init(numBits, new SecureRandom());

            SecretKey secretKey = keyGenerator.generateKey();
            String format = secretKey.getFormat();
            String secretKeyAlgorithm = secretKey.getAlgorithm();
            byte[] encoded = secretKey.getEncoded();
            String base64Encoded = Base64.encodeBase64String(encoded);
            logger.info("getSecretKey format = [{}] algorithm = [{}] base64Encoded = [{}]", format,
                secretKeyAlgorithm, base64Encoded);

            return Optional.of(secretKey);
        } catch (NoSuchAlgorithmException e) {
            logger.error("getSecretKey Exception = [{}]", e.getMessage(), e);

            return Optional.empty();
        }
    }

    public static Optional<byte[]> encrypt(byte[] input, String algorithm, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] output = cipher.doFinal(input);

            return Optional.of(output);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            logger.error("encrypt Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static Optional<byte[]> decrypt(byte[] input, String algorithm, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] output = cipher.doFinal(input);

            return Optional.of(output);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            logger.error("decrypt Exception = [{}]", e.getMessage(), e);

            return Optional.empty();
        }
    }
}
