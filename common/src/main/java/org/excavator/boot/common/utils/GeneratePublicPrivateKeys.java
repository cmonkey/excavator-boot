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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

public class GeneratePublicPrivateKeys {
    private final static Logger logger = LoggerFactory.getLogger(GeneratePublicPrivateKeys.class);

    public static Optional<GeneratePublicPrivateKey> generateKeys(String keyAlgorithm, int numBits) {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyAlgorithm);

            keyPairGenerator.initialize(numBits);

            KeyPair keyPair = keyPairGenerator.genKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            logger.info("Generating key/value pair using {} algorithm", privateKey.getAlgorithm());

            byte[] privateKeyBytes = privateKey.getEncoded();
            byte[] publicKeyBytes = publicKey.getEncoded();

            String formatPrivate = privateKey.getFormat(); // PKCS#8
            String formatPublic = publicKey.getFormat(); // X.509

            String privateKeyEncodeBase64String = Base64.encodeBase64String(privateKeyBytes);
            String publicKeyEncodeBase64String = Base64.encodeBase64String(publicKeyBytes);

            logger.info("Private Key = {}", privateKeyEncodeBase64String);
            logger.info("Public Key = {}", publicKeyEncodeBase64String);

            logger.info("formatPrivate = {}", formatPrivate);
            logger.info("formatPublic = {}", formatPublic);

            GeneratePublicPrivateKey generatePublicPrivateKey = new GeneratePublicPrivateKey(
                privateKeyEncodeBase64String, publicKeyEncodeBase64String);

            return Optional.of(generatePublicPrivateKey);
        } catch (NoSuchAlgorithmException e) {
            logger.error("generateKeys Exception = {}", e);

            return Optional.empty();
        }
    }

    public static Optional<PublicPrivateKey> getPublicPrivateKey(String keyAlgorithm,
                                                                 GeneratePublicPrivateKey generatePublicPrivateKey) {
        try {
            logger.info("getPublicPrivateKey algorithm = {}", keyAlgorithm);

            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);

            byte[] privateBytes = Base64.decodeBase64(generatePublicPrivateKey
                .getPrivateKeyEncodeBase64String());
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            byte[] publicBytes = Base64.decodeBase64(generatePublicPrivateKey
                .getPublicKeyEncodeBase64String());
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            PublicPrivateKey publicPrivateKey = new PublicPrivateKey(privateKey, publicKey);

            return Optional.of(publicPrivateKey);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("getPublicPrivateKey Exception = {}", e);
            return Optional.empty();
        }
    }
}
