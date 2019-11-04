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
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.excavator.boot.common.enums.ResolveEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.Certificate;
import java.security.spec.*;
import java.util.Enumeration;
import java.util.Optional;

public class GeneratePublicPrivateKeys {
    private final static Logger logger            = LoggerFactory
                                                      .getLogger(GeneratePublicPrivateKeys.class);
    private final static int    MAX_DECRYPT_BLACK = 256;
    private final static int    MAX_ENCRYPT_BLACK = 128;

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
            logger.error("generateKeys Exception = [{}]", e.getMessage(), e);

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

    public static Optional<GeneratePublicPrivateKey> generateKeysByEC(String keyAlgorithm, boolean isGenerator) {
        Optional<KeyPairGenerator> optionalKeyPairGenerator = isGenerator? generator(): generatorByEC();

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalKeyPairGenerator.map(keyPairGenerator -> {

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            return generatePublicPrivateKey(
                    privateKey, publicKey);
        }).orElse(null);

        return Optional.ofNullable(generatePublicPrivateKey);

    }

    public static Optional<GeneratePublicPrivateKey> generateKeysByEC() {
        return generateKeysByEC("EC", false);
    }

    public static Optional<GeneratePublicPrivateKey> generateKeysByEC(String keyAlgorithm) {
        return generateKeysByEC(keyAlgorithm, false);
    }

    private static Optional<KeyPairGenerator> generatorByEC() {
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

            return Optional.of(keyPairGenerator);

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            logger.error("generateKeysByEC Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }

    private static Optional<KeyPairGenerator> generator() {
        try {
            // 获取SM2椭圆曲线的参数
            final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
            // 获取一个椭圆曲线类型的密钥对生成器
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC",
                new BouncyCastleProvider());
            // 使用SM2参数初始化生成器
            keyPairGenerator.initialize(sm2Spec);

            return Optional.of(keyPairGenerator);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            logger.error("generator Exception = [{}]", e.getMessage(), e);
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
            logger.error("getPublicPrivateKey Exception = [{}]", e.getMessage(), e);
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

    public static Optional<byte[]> encrypt(byte[] input, String algorithm, PublicKey publicKey,
                                           boolean isBlockDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] output = isBlockDecrypt ? doFinalExt(input, cipher, MAX_ENCRYPT_BLACK) : cipher
                .doFinal(input);
            return Optional.ofNullable(output);
        } catch (Exception e) {
            logger.error("encrypt Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }

    }

    public static Optional<byte[]> encrypt(byte[] input, String algorithm, PublicKey publicKey) {
        return encrypt(input, algorithm, publicKey, false);
    }

    public static Optional<byte[]> encryptBySM2(byte[] input, PublicKey publicKey) {

        try {
            AsymmetricKeyParameter asymmetricKeyParameter = PublicKeyFactory.createKey(publicKey
                .getEncoded());

            SM2Engine sm2Engine = new SM2Engine();
            sm2Engine.init(true, new ParametersWithRandom(asymmetricKeyParameter,
                new SecureRandom()));
            byte[] enc = sm2Engine.processBlock(input, 0, input.length);
            return Optional.ofNullable(enc);
        } catch (IOException | InvalidCipherTextException e) {
            logger.error("encryptBySM2 Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static Optional<byte[]> decrypt(byte[] input, String algorithm, PrivateKey privateKey,
                                           boolean isBlockDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] output = isBlockDecrypt ? doFinalExt(input, cipher, MAX_DECRYPT_BLACK) : cipher
                .doFinal(input);

            return Optional.ofNullable(output);
        } catch (Exception e) {
            logger.error("decrypt Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static Optional<byte[]> decrypt(byte[] input, String algorithm, PrivateKey privateKey) {
        return decrypt(input, algorithm, privateKey, false);
    }

    public static Optional<byte[]> decryptBySM2(byte[] input, PrivateKey privateKey) {
        try {
            AsymmetricKeyParameter asymmetricKeyParameter = PrivateKeyFactory.createKey(privateKey
                .getEncoded());
            SM2Engine sm2Engine = new SM2Engine();
            sm2Engine.init(false, asymmetricKeyParameter);

            byte[] dec = sm2Engine.processBlock(input, 0, input.length);

            return Optional.ofNullable(dec);
        } catch (IOException | InvalidCipherTextException e) {
            logger.error("decryptBySM2 Exception = [{}]", e.getMessage(), e);
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
            logger.error("sign Exception = [{}]", e.getMessage(), e);
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
            logger.error("verifySign Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static Optional<PrivateKey> getPrivateKey(String keyAlgorithm, byte[] keyBytes) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory
                .getInstance(keyAlgorithm, new BouncyCastleProvider());
            return Optional.ofNullable(keyFactory.generatePrivate(spec));
        } catch (Exception e) {
            logger.error("getPrivate Exception = [{}]", e.getMessage(), e);

            return Optional.empty();
        }
    }

    public static Optional<PrivateKey> getPrivateKeyByPKCS12(byte[] keyBytes, String password) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(keyBytes)) {
                keyStore.load(inputStream, password.toCharArray());
            } catch (IOException e) {
                logger.error("getPrivateByPKCS12 Exception = [{}]", e.getMessage(), e);
                return Optional.empty();
            }

            Enumeration<String> aliases = keyStore.aliases();
            String keyAlias = "";
            while (aliases.hasMoreElements()) {
                keyAlias = aliases.nextElement();
            }

            return Optional.ofNullable((PrivateKey) keyStore.getKey(keyAlias,
                password.toCharArray()));
        } catch (Exception e) {
            logger.error("getPrivateByPKCS12 Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static Optional<PublicKey> getPublicKey(String keyAlgorithm, byte[] keyBytes) {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory
                .getInstance(keyAlgorithm, new BouncyCastleProvider());
            return Optional.ofNullable(keyFactory.generatePublic(spec));
        } catch (Exception e) {
            logger.error("getPublicKey Exception = [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }
    public static Optional<PublicKey> getX509PublicKey(InputStream inputStream) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(inputStream);
            PublicKey publicKey = certificate.getPublicKey();
            String type = certificate.getType();
            logger.info("type = {} ", type);

            return Optional.ofNullable(publicKey);
        } catch (CertificateException e) {
            logger.error("getX509PublicKey Exception [{}]", e.getMessage(), e);
            return Optional.empty();
        }
    }

}
