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
}
