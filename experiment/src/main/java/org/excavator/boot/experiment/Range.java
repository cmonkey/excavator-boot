package org.excavator.boot.experiment;

public record Range(int start, int end) {
    public Range{
        if (end <= start) {
            throw new IllegalArgumentException();
        }
    }
}
