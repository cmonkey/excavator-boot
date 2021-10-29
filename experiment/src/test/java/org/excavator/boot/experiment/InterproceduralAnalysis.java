package org.excavator.boot.experiment;

public class InterproceduralAnalysis {
    private int absoluteDifference(int x, int y){
        if(y > x){
            return y - x;
        }else{
            return x - y;
        }
    }
    public void analyze(){
        int x= absoluteDifference(12, 15) + absoluteDifference(9, 2);
        if(x == 10){
            System.out.println(x);
        }
    }
}
