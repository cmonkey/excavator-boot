package org.excavator.boot.experiment.reference.a;

public class Outer {
    private static class Inner{

        @FunctionalInterface
        public interface Supplier{
            int getInt();
        }

    }

    public static int invoke(Inner.Supplier supplier){
        var v = supplier.getInt();
        return v;
    }
}
