package org.excavator.boot.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.util.Optional;

public class GeneratePublicPrivateKeys {
    private final static Logger logger = LoggerFactory.getLogger(GeneratePublicPrivateKeys.class);

    public static Optional<GeneratePublicPrivateKey> generateKeys(String keyAlgorithm, int numBits){

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

            GeneratePublicPrivateKey generatePublicPrivateKey = new GeneratePublicPrivateKey(privateKeyEncodeBase64String, publicKeyEncodeBase64String);

            return Optional.of(generatePublicPrivateKey);
        }catch(NoSuchAlgorithmException e){
            logger.error("generateKeys Exception = {}", e);

            return Optional.empty();
        }
    }
}
