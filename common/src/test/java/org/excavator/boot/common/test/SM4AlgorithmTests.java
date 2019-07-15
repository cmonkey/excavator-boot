package org.excavator.boot.common.test;

import org.excavator.boot.common.utils.GenerateSymmetricencryption;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SM4AlgorithmTests {

    private static final Logger                     logger          = LoggerFactory
                                                                        .getLogger(SM4AlgorithmTests.class);

    private static final AtomicReference<SecretKey> atomicReference = new AtomicReference<>();
    private static final String                     ALGORITHM       = "SM4";
    private static final String                     DATA            = "Hello SM4";

    @Test
    @DisplayName("test sm4 generator")
    @Order(1)
    public void testGenerator(){
        Optional<SecretKey> secretKeyOptional = GenerateSymmetricencryption.getSecretKey(ALGORITHM, 126);

        assertEquals(true, secretKeyOptional.isPresent());

        secretKeyOptional.ifPresent(key -> {
            atomicReference.set(key);
            assertEquals(ALGORITHM, key.getAlgorithm());
        });
    }

    @Test
    @DisplayName("test sm4 encrypt")
    @Order(2)
    public void testEncrypt() {
        SecretKey secretKey = atomicReference.get();

        Optional<byte[]> encryptOptional = GenerateSymmetricencryption.encrypt(
            DATA.getBytes(StandardCharsets.UTF_8), ALGORITHM, secretKey);

        assertEquals(true, encryptOptional.isPresent());
    }

    @Test
    @DisplayName("test sm4 long block encrypt")
    @Order(3)
    public void testLongBlockEncrypt(){
        SecretKey secretKey = atomicReference.get();
        StringBuilder builder = new StringBuilder();
        IntStream.range(0, 10000).forEach(i -> {
            builder.append("AAA");
        });

        Optional<byte[]> encryptOptional = GenerateSymmetricencryption
                .encrypt(builder.toString().getBytes(StandardCharsets.UTF_8), ALGORITHM, secretKey);

        assertEquals(true, encryptOptional.isPresent());
    }

    @Test
    @DisplayName("test sm4 decrypt")
    @Order(4)
    public void testDecrypt() {

        SecretKey secretKey = atomicReference.get();

        Optional<byte[]> encryptOptional = GenerateSymmetricencryption.encrypt(
            DATA.getBytes(StandardCharsets.UTF_8), ALGORITHM, secretKey);

        assertEquals(true, encryptOptional.isPresent());

        byte[] encryptData = encryptOptional.get();

        Optional<byte[]> decryptOptional = GenerateSymmetricencryption.decrypt(encryptData,
            ALGORITHM, secretKey);

        assertEquals(true, decryptOptional.isPresent());

        byte[] decryptData = decryptOptional.get();

        String decryptText = new String(decryptData, StandardCharsets.UTF_8);

        logger.info("decryptTest = [{}]", decryptText);

        assertEquals(DATA, decryptText);
    }


}
