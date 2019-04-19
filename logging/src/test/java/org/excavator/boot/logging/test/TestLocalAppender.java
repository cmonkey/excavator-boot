package org.excavator.boot.logging.test;

import org.excavator.boot.logging.LocalAppender;
import org.excavator.boot.logging.OtherLogProductingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import static org.assertj.core.api.Assertions.assertThat;

public class TestLocalAppender {

    @BeforeAll
    public static void setup(){
        LocalAppender.pauseTillLogbackReady();
    }

    @Test
    @ResourceLock(value = "LOGING", mode = ResourceAccessMode.READ_WRITE)
    public void testLocalAppenderA(){
        OtherLogProductingService service = new OtherLogProductingService();

        LocalAppender localAppender = LocalAppender.initialize(new String[]{"org.excavator.boot.logging.OtherLogProductingService"});

        service.writeSomeLoggingStatements("Other logging service A");

        assertThat(localAppender.getEvents()).extracting("message")
                .containsOnly("Let's a assert some logs! Other logging service A", "This message is in a separate thread");

        LocalAppender.cleanup(localAppender);
    }

    @Test
    @ResourceLock(value = "LOGING", mode = ResourceAccessMode.READ_WRITE)
    public void testLocalAppenderB(){
        OtherLogProductingService service = new OtherLogProductingService();

        LocalAppender localAppender = LocalAppender.initialize(new String[]{"org.excavator.boot.logging.OtherLogProductingService"});

        service.writeSomeLoggingStatements("Other logging service B");

        assertThat(localAppender.getEvents()).extracting("message")
                .containsOnly("Let's a assert some logs! Other logging service B", "This message is in a separate thread");

        LocalAppender.cleanup(localAppender);
    }

    @Test
    @ResourceLock(value = "LOGING", mode = ResourceAccessMode.READ)
    public void justAnotherTest() {
        OtherLogProductingService service = new OtherLogProductingService();
        service.writeSomeLoggingStatements("Local Appender");
    }

    @Test
    @ResourceLock(value = "LOGING", mode = ResourceAccessMode.READ)
    public void yetAnotherTest(){
        OtherLogProductingService service = new OtherLogProductingService();
        service.writeSomeLoggingStatements("Local Appender");
    }
}
