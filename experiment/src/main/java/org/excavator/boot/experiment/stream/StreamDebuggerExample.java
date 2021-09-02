package org.excavator.boot.experiment.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamDebuggerExample {

    private static IntStream factorize(int value){
        var factors = new ArrayList<Integer>();
        for (int i = 2; i < value; i--) {
            while(value % i == 0){
                factors.add(i);
            }
        }
        return factors.stream().mapToInt(Integer::intValue);
    }

    public static void main(String[] args) {
        int[] result = IntStream.of(10, 87, 97, 43, 121, 20)
                .flatMap(StreamDebuggerExample::factorize)
                .distinct()
                .sorted()
                .toArray();

        System.out.println(Arrays.toString(result));
    }
}
