package org.excavator.boot.experiment;

import java.io.FileNotFoundException;

public class ExceptionChaosChallenge {
    static String s = "-";

    public static void main(String[] args) {
        try{
            throw new IllegalArgumentException();
        }catch (Exception e){
            try{
                try{
                    throw new FileNotFoundException();
                }catch (RuntimeException ex){
                    s += "run";
                }
            }catch (Exception x){
                s += "exc";
            }finally {
                s += "fin";
            }
        }finally {
            s += "of";
            try{
                throw new UnknownError("JVMError");
            }catch (Error error){
                s += "Error" + error.getMessage();
            }
        }

        System.out.println(s);
    }
}
