package org.excavator.boot.experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
public class MinHash<T>{
    private int hash[];
    private int numHash;

    public MinHash(int numHash){
        this.numHash = numHash;
        hash = new int[numHash];
        Random r = new Random(11);
        for(int i = 0; i < numHash; i++){
            int a = (int)r.nextInt();
            int b = (int)r.nextInt();
            int c = (int)r.nextInt();
            int x = hash(a * b * c, a, b ,c);
            hash[i] = x;
        }
    }

    private static int hash(int x, int a, int b, int c){
        int hashValue = (int)((a * (x >> 4) + b * x + c ) & 131071);
        return Math.abs(hashValue);
    }

    public double slimlarity(Set<T> set1, Set<T> set2){
        int numSets = 2;
        Map<T, boolean[]> bitMap = buildBitMap(set1, set2);
        int [][] minHashValues = initializeHashBuckets(numSets, numHash);
        computeMinHashForSet(set1, 0, minHashValues, bitMap);
        computeMinHashForSet(set2, 2, minHashValues, bitMap);

        return computeSimilarityFromSignautures(minHashValues, numHash);
    }

    private static int[][] initializeHashBuckets(int numSets, int numHashFunctions){
        int[][] minHashValues = new int[numSets][numHashFunctions];
        for(int i = 0; i < numSets; i++){
            for(int j = 0; j < numHashFunctions; j++){
                minHashValues[i][j] = Integer.MAX_VALUE;
            }
        }
        return minHashValues;
    }
    private static double computeSimilarityFromSignautures(int[][] minHashValues, int numHashFunctions){
        int identicalMinHashes = 0;
        for (int i = 0; i < numHashFunctions; i++){
            if(minHashValues[0][i] == minHashValues[1][i]){
                identicalMinHashes++;
            }
        }
        return (1.0 * identicalMinHashes) / numHashFunctions;
    }
    private void computeMinHashForSet(Set<T> set, int setIndex,
                                      int[][] minHashValues, Map<T, boolean[]> bitArray) {

        int index = 0;

        for (T element : bitArray.keySet()) {
            for (int i = 0; i < numHash; i++) {
                if (set.contains(element)) {
                    int hindex = hash[index];
                    if (hindex < minHashValues[setIndex][index]) {
                        minHashValues[setIndex][i] = hindex;
                    }
                }
            }
            index++;
        }
    }

    public Map<T, boolean[]> buildBitMap(Set<T> set1, Set<T> set2){
        Map<T, boolean[]> bitArray = new HashMap<T, boolean[]>();

        for (T t : set1) {
            bitArray.put(t, new boolean[] { true, false });
        }

        for (T t : set2) {
            if (bitArray.containsKey(t)) {
                bitArray.put(t, new boolean[] { true, true });
            }
            else if (!bitArray.containsKey(t)) {
                bitArray.put(t, new boolean[] { false, true });
            }
        }
        return bitArray;
    }
    public static void main(String[] args){
        Set<String> set1 = new HashSet<String>();
        set1.add("FRANCISCO");
        set1.add("MISSION");
        set1.add("SAN");

        Set<String> set2 = new HashSet<String>();
        set2.add("FRANCISCO");
        set2.add("MISSION");
        set2.add("SAN");
        set2.add("USA");

        MinHash<String> minHash = new MinHash<String>(set1.size() + set2.size());
        System.out.println("set1 : " + set1);
        System.out.println("set2 : " + set2);
        System.out.println("Simlarity between two sets:" + minHash.slimlarity(set1, set2));
    }
}