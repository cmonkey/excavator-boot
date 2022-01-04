package org.excavator.boot.experiment;

public class Reflection {

    public static boolean get(){
        return true;
    }

    public static void main(String[] args) throws Exception{
        var method = Reflection.class.getMethod("get");
        for (int i = 0; i < 30; i++) {
            var value = method.invoke(null);
            System.out.println(value == Boolean.TRUE);
        }
    }
}
