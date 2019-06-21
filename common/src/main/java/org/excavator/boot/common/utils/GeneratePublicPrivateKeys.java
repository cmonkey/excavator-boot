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

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.excavator.boot.common.enums.ResolveEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

public class GeneratePublicPrivateKeys {
    private final static Logger logger    = LoggerFactory
                                              .getLogger(GeneratePublicPrivateKeys.class);
    private final static int    MAX_BLACK = 128;

    public static Optional<GeneratePublicPrivateKey> generateKeys(String keyAlgorithm, int numBits) {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyAlgorithm);

            keyPairGenerator.initialize(numBits);

            KeyPair keyPair = keyPairGenerator.genKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            GeneratePublicPrivateKey generatePublicPrivateKey = generatePublicPrivateKey(
                privateKey, publicKey);

            return Optional.of(generatePublicPrivateKey);

        } catch (NoSuchAlgorithmException e) {
            logger.error("generateKeys Exception = {}", e);

            return Optional.empty();
        }
    }

    private static GeneratePublicPrivateKey generatePublicPrivateKey(PrivateKey privateKey,
                                                                     PublicKey publicKey) {
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

        String privateEncodeHexString = Hex.encodeHexString(privateKeyBytes);
        String publicEncodeHexString = Hex.encodeHexString(publicKeyBytes);

        GeneratePublicPrivateKey generatePublicPrivateKey = new GeneratePublicPrivateKey(
            privateEncodeHexString, publicEncodeHexString, privateKeyEncodeBase64String,
            publicKeyEncodeBase64String);

        return generatePublicPrivateKey;
    }

    public static Optional<GeneratePublicPrivateKey> generateKeysByEC(String keyAlgorithm) {
        try {
            // Get the SM2 elliptic curve recommended parameters
            X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");

            // Construct EC algorithm parameters

            ECNamedCurveParameterSpec ecNamedCurveParameterSpec = new ECNamedCurveParameterSpec(
            // Set the OID of the sm2 algorithm
                GMObjectIdentifiers.sm2p256v1.toString(), x9ECParameters.getCurve(),
                x9ECParameters.getG(), x9ECParameters.getN());

            // Create a key pair generator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC",
                new BouncyCastleProvider());

            // Initialize the key generator using the algorithm are of SM2
            keyPairGenerator.initialize(ecNamedCurveParameterSpec, new SecureRandom());

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            GeneratePublicPrivateKey generatePublicPrivateKey = generatePublicPrivateKey(
                privateKey, publicKey);

            return Optional.of(generatePublicPrivateKey);

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            logger.error("generateKeysByEC Exception = {}", e);
            return Optional.empty();
        }

    }

    public static Optional<PublicPrivateKey> getPublicPrivateKey(String keyAlgorithm,
                                                                 GeneratePublicPrivateKey generatePublicPrivateKey,
                                                                 ResolveEnum resolveEnum) {
        try {
            logger.info("getPublicPrivateKey algorithm = {}", keyAlgorithm);
            PublicPrivateKey publicPrivateKey = null;
            switch (resolveEnum) {
                case BASE64:
                    publicPrivateKey = resolveBase64(keyAlgorithm, generatePublicPrivateKey);
                    break;
                case HEX:
                    publicPrivateKey = resolveHex(keyAlgorithm, generatePublicPrivateKey);
                    break;
                default:
                    break;

            }
            return Optional.ofNullable(publicPrivateKey);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | DecoderException e) {
            logger.error("getPublicPrivateKey Exception = {}", e);
            return Optional.empty();
        }
    }

    private static PublicPrivateKey resolveBase64(String keyAlgorithm,
                                                  GeneratePublicPrivateKey generatePublicPrivateKey)
                                                                                                    throws NoSuchAlgorithmException,
                                                                                                    InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm, new BouncyCastleProvider());

        byte[] privateBytes = Base64.decodeBase64(generatePublicPrivateKey
            .getPrivateKeyEncodeBase64String());
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        byte[] publicBytes = Base64.decodeBase64(generatePublicPrivateKey
            .getPublicKeyEncodeBase64String());
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        PublicPrivateKey publicPrivateKey = new PublicPrivateKey(privateKey, publicKey);

        return publicPrivateKey;
    }

    private static PublicPrivateKey resolveHex(String keyAlgorithm,
                                               GeneratePublicPrivateKey generatePublicPrivateKey)
                                                                                                 throws InvalidKeySpecException,
                                                                                                 DecoderException,
                                                                                                 NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm, new BouncyCastleProvider());

        byte[] privateBytes = Hex
            .decodeHex(generatePublicPrivateKey.getPrivateKeyEncodeHexString());
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        byte[] publicBytes = Hex.decodeHex(generatePublicPrivateKey.getPublicKeyEncodeHexString());
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        PublicPrivateKey publicPrivateKey = new PublicPrivateKey(privateKey, publicKey);

        return publicPrivateKey;
    }

    private static byte[] doFinalExt(byte[] input, Cipher cipher, int maxBlock) throws IOException,
                                                                               BadPaddingException,
                                                                               IllegalBlockSizeException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            int inputLen = input.length;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > maxBlock) {
                    cache = cipher.doFinal(input, offSet, maxBlock);
                } else {
                    cache = cipher.doFinal(input, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * maxBlock;
            }
            byte[] encryptedData = out.toByteArray();
            return encryptedData;
        }
    }

    public static Optional<byte[]> encrypt(byte[] input, String algorithm, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] output = doFinalExt(input, cipher, MAX_BLACK);
            return Optional.ofNullable(output);
        } catch (Exception e) {
            logger.error("encrypt Exception = {}", e);
            return Optional.empty();
        }

    }

    public static Optional<byte[]> decrypt(byte[] input, String algorithm, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] output = doFinalExt(input, cipher, MAX_BLACK);

            return Optional.ofNullable(output);
        } catch (Exception e) {
            logger.error("decrypt Exception = {}", e);
            return Optional.empty();
        }
    }

    public static Optional<byte[]> sign(byte[] input, String algorithm, PrivateKey privateKey) {

        try {
            Signature signature = Signature.getInstance(algorithm, new BouncyCastleProvider());
            signature.initSign(privateKey);
            signature.update(input);
            return Optional.ofNullable(signature.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            logger.error("sign Exception = {}", e);
            return Optional.empty();
        }
    }

    public static Optional<Boolean> verifySign(byte[] signInput, byte[] dataInput,
                                               String algorithm, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(algorithm, new BouncyCastleProvider());
            signature.initVerify(publicKey);
            signature.update(dataInput);
            boolean verify = signature.verify(signInput);

            return Optional.ofNullable(verify);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            logger.error("verifySign Exception = {}", e);
            return Optional.empty();
        }
    }
}
