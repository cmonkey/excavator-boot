package org.excavator.boot.common.test;

import org.excavator.boot.common.utils.GeneratePublicPrivateKey;
import org.excavator.boot.common.utils.GeneratePublicPrivateKeys;
import org.excavator.boot.common.utils.PublicPrivateKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratorPublicPrivateKeyTests {

    @Test
    @DisplayName("testGeneratorByRSA")
    public void testGenerator(){

        String algorithm = "RSA";

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys.generateKeys(algorithm, 2048);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys.getPublicPrivateKey(algorithm, generatePublicPrivateKey);

        assertEquals(true, optionalPublicPrivateKey.isPresent());
    }

    @Test
    @DisplayName("testGeneratorByDSA")
    public void testGeneratorByDSA(){

        String algorithm = "DSA";

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys.generateKeys(algorithm, 1024);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys.getPublicPrivateKey(algorithm, generatePublicPrivateKey);

        assertEquals(true, optionalPublicPrivateKey.isPresent());
    }

    @Test
    @DisplayName("testGeneratorByDH")
    public void testGeneratorByDH(){
        String algorithm = "DH";

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys.generateKeys(algorithm, 512);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());

        GeneratePublicPrivateKey generatePublicPrivateKey = optionalGeneratePublicPrivateKey.get();

        Optional<PublicPrivateKey> optionalPublicPrivateKey = GeneratePublicPrivateKeys.getPublicPrivateKey(algorithm, generatePublicPrivateKey);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());
    }
}
