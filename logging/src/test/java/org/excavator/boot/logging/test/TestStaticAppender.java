package org.excavator.boot.logging.test;

import org.excavator.boot.logging.LogProducingService;
import org.excavator.boot.logging.StaticAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStaticAppender {

    @BeforeEach
    public void clearLoggingStatements() {
        StaticAppender.clearEvents();
    }

    @Test
    public void testAssertingLoggingStatemenetsA(){
        LogProducingService service = new LogProducingService();
        service.writeSomeLoggingStatemenets("A");

        assertThat(StaticAppender.getEvents()).extracting("message")
                .containsOnly("Let's assert some logs! A", "this message is in a separate thread");
    }
}
