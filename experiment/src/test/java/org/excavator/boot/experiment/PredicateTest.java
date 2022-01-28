package org.excavator.boot.experiment;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.Set;

public class PredicateTest {

    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PredicateTest.class);

    @Test
    public void testPredicate(){
        var set = Set.of("a", "b", "c");

        set.stream().filter(s -> {
            LOGGER.info("filter: " + s);
            return s.length() > 1;
        }).findFirst().ifPresent(s -> {
            LOGGER.info("findFirst: " + s);
        });
    }
}
