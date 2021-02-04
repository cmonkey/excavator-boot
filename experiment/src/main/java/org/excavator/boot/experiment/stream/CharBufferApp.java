package org.excavator.boot.experiment.stream;

import java.nio.charset.Charset;

public class CharBufferApp {

    public static void printDefaults(){
        System.out.printf("Default CharacterSet: %s%n", Charset.defaultCharset().name());
        System.out.printf("Character Bytes: %s%n", Character.BYTES);
    }
}
