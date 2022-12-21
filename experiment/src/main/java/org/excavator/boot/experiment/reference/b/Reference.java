package org.excavator.boot.experiment.reference.b;

import org.excavator.boot.experiment.reference.a.Outer;

public class Reference {
    public static void main(String[] args) {
        var r = Outer.invoke(() -> 42);
        System.out.println(r);
    }
}
