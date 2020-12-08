package org.excavator.boot.experiment.stream;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Stream {
    private final Queue<Integer> numbers;

    public Stream(List<Integer> numbers){
        this.numbers = new LinkedList<>(numbers);
    }

    public Integer next(){
        return numbers.poll();
    }
}
