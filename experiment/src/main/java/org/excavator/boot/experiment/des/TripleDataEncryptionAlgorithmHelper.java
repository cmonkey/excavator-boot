package org.excavator.boot.experiment.des;

import javax.crypto.spec.SecretKeySpec;

public class TripleDataEncryptionAlgorithmHelper {

    public static SecretKeySpec buildSecretKeySpec(byte[] secretKey, String algorithm){
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, algorithm);
        return secretKeySpec;
    }
}
