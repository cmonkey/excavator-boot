package org.excavator.boot.common.test;

import org.excavator.boot.common.utils.GeneratePublicPrivateKey;
import org.excavator.boot.common.utils.GeneratePublicPrivateKeys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratorPublicPrivateKeyTests {

    @Test
    @DisplayName("testGeneratorByRSA")
    public void testGenerator(){

        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys.generateKeys("RSA", 2048);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());
    }

    @Test
    @DisplayName("testGeneratorByDSA")
    public void testGeneratorByDSA(){
        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys.generateKeys("DSA", 1024);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());
    }

    @Test
    @DisplayName("testGeneratorByDH")
    public void testGeneratorByDH(){
        Optional<GeneratePublicPrivateKey> optionalGeneratePublicPrivateKey = GeneratePublicPrivateKeys.generateKeys("DH", 512);

        assertEquals(true, optionalGeneratePublicPrivateKey.isPresent());
    }
}
