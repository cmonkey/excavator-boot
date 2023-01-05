package org.excavator.boot.experiment;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MergeSort extends RecursiveAction {
    private static final int  SEQUENTIAL_THRESHOLD = 50;
    final int[] numbers;
    final int startPos, endPos;
    final int[] result;

    public MergeSort(int[] numbers, int startPos, int endPos) {
        super();
        this.numbers = numbers;
        this.startPos = startPos;
        this.endPos = endPos;
        this.result = new int[endPos - startPos];
    }

    public int size(){
        return endPos - startPos;
    }

    private void merge(MergeSort left, MergeSort right){
        int i = 0, leftPos = 0, rightPos = 0, leftSize = left.size(), rightSize= right.size();
        while (leftPos < leftSize && rightPos < rightSize)
            result[i++] = (left.result[leftPos] <= right.result[rightPos]) ? left.result[leftPos++]
                    : right.result[rightPos++];
        while(leftPos < leftSize){
            result[i++] = left.result[leftPos++];
        }

        while(rightPos < rightSize){
            result[i++] = right.result[rightPos++];
        }
    }

    @Override
    protected void compute() {
        if(size() < SEQUENTIAL_THRESHOLD){
            System.arraycopy(numbers, startPos, result, 0, size());
        }else{
            int midpoint = size() / 2;
            var left = new MergeSort(numbers, startPos, startPos + midpoint);
            var right = new MergeSort(numbers, startPos + midpoint, endPos);
            ForkJoinTask.invokeAll(left, right);
            merge(left, right);
        }

    }
}
