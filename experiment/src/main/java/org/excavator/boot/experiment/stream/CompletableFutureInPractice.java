package org.excavator.boot.experiment.stream;

import org.excavator.boot.experiment.stream.Stream;

import java.util.ArrayList;
import java.util.List;

public class CompletableFutureInPractice {

    Stream merge(Stream inputA, Stream inputB){

        List<Integer> result = new ArrayList<>();

        Integer headA = inputA.next();
        Integer headB = inputA.next();

        while(headA != null || headB != null){
            if(headA == null || headB != null && headA > headB){
                result.add(headB);
                headB = inputB.next();
            }else{
                result.add(headA);
                headA = inputA.next();
            }
        }

        return new Stream(result);
    }
}
