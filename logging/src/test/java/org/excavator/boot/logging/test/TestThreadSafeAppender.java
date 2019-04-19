package org.excavator.boot.logging.test;

import org.excavator.boot.logging.LogProducingService;
import org.excavator.boot.logging.ThreadSafeAppender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestThreadSafeAppender {

    @BeforeAll
    public static void setup(){
        ThreadSafeAppender.pauseTillLogBackReady();
    }

    @BeforeEach
    public void clearLoggingStatements(){
        ThreadSafeAppender.clearEvents();
    }

    @Test
    public void testAssertingLoggingStatementsA() {
        LogProducingService service = new LogProducingService();
        service.writeSomeLoggingStatemenets("A");
        assertThat(ThreadSafeAppender.getEvents()).extracting("message")
                .containsOnly("Let's assert some logs! A");
    }

    @Test
    public void testAssertingLoggingStatementsB(){
        LogProducingService service = new LogProducingService();
        service.writeSomeLoggingStatemenets("B");
        assertThat(ThreadSafeAppender.getEvents())
                .extracting("message")
                .containsOnly("Let's assert some logs! B");
    }
}
