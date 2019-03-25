package org.excavator.boot.lock.enums;

public enum  RedissonMode {
    SINGLE("single"),
    SENTINEL("sentinel"),
    CLUSTER("cluster");

    private String mode;

    RedissonMode(String mode) {
        this.mode = mode;
    }
}
