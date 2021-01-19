package org.excavator.boot.experiment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteArrayTest {

    @Test
    @DisplayName("testByteArrayToStream")
    public void byteArrayToStream(){
        var bytes= new byte[]{0xC, 0xA, 0xF, 0xE, 0xB, 0xA, 0xB, 0xE};
        var buffer = ByteBuffer.wrap(bytes);
        var message = Stream.generate(() -> buffer.get())
                .limit(buffer.capacity())
                .map(b -> Integer.toHexString(b))
                .collect(Collectors.joining());

        assertEquals("cafebabe", message);
    }
}
