package org.excavator.boot.experiment.continuation;
import static java.lang.System.out;

public class ContApp {

    static void add(int a, int b, Cont<Integer> cont){
        cont.apply(a + b);
    }
    public static void main(String[] args) {
        System.out.println(1 + 2 + 3);
        add(1, 2, partialSum ->
                add(partialSum, 3, sum ->
                        print(sum, unit ->
                                System.exit(0))));
    }
    static void print(int n, Cont<Void> cont){
        out.println(n);
        cont.apply(null);
    }

    static void iff(boolean expr, Cont<Boolean> trueBranch, Cont<Boolean> falseBranch){
        if(expr){
            trueBranch.apply(true);
        }else{
            falseBranch.apply(false);
        }
    }

    static long sum(int from, int to){
        long sum = 0;
        for(int i = from; i <= to; i++){
            sum += i;
        }

        return sum;
    }

    static long sum_rec(int from, int to){
        return (from > to)
                ? 0
                : from + sum_rec(from + 1, to);
    }

}
