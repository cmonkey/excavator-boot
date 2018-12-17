package org.excavator.boot.lombok.simulation.test;

import org.excavator.boot.lombok.simulation.annotation.Getter;

@Getter
public class App {
    private String value;

    public App(String value) {
        this.value = value;
    }
}
