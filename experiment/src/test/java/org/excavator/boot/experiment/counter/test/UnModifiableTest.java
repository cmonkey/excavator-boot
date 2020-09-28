package org.excavator.boot.experiment.counter.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UnModifiableTest {
    final static java.util.Set<String> UNMODIFIABLESET = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Hello", "World")));
    final static Set<String> OF_SET = Set.of("Hello", "World");
    @Test
    @DisplayName("test unmodifiable")
    public void testUnModifiable(){
        assertEquals(UNMODIFIABLESET.contains(null), false);
        assertThrows(NullPointerException.class, () -> {
            OF_SET.contains(null);
        });
    }
}
