package org.excavator.boot.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
            logger.error("getSecretKey Exception = [{}]", e);

            return Optional.empty();
        }
    }
}
