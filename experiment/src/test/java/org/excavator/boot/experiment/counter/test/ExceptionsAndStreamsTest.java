package org.excavator.boot.experiment.counter.test;

import org.excavator.boot.experiment.stream.ExceptionsAndStreams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionsAndStreamsTest {
    @Test
    @DisplayName("testConvertingCheckedIntoRuntimeExceptions")
    public void testConvertingCheckedIntoRuntimeExceptions(){
        var exceptionsAndStreams = new ExceptionsAndStreams();
        assertThrows(RuntimeException.class, () -> {
            exceptionsAndStreams.convertingCheckedIntoRuntimeExceptions();
        });
    }
}
