package algorithms.companies.amazon.amazon;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Amazon_14_OptimizingBoxWeights {
    @Test
    public void minimalHeaviestSetATest(){
        List<Integer> input = new ArrayList<>();
        input.add(6);
        input.add(5);
        input.add(3);
        input.add(2);
        input.add(4);
        input.add(1);
        input.add(2);
        List<Integer> result = minimalHeaviestSetA(input);
    }

    public List<Integer> minimalHeaviestSetA(List<Integer> arr) {
        List<Integer> result = new ArrayList<>();
        if(arr == null || arr.size() < 3)
            return result;


        int totalWeight = 0;
        HashMap<Integer, Integer> mapWeights = new HashMap<>();
        for(int weight: arr){
            totalWeight += weight;
            mapWeights.put(weight, mapWeights.getOrDefault(weight, 0)+1);
        }
        if(mapWeights.size() == 1) return result;

        List<Integer> subsetA = new ArrayList<>();
        List<Integer> subsetB = new ArrayList<>();
        int subsetAWeights = 0, subsetBWeights = totalWeight;
        subsetB.addAll(arr);

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        queue.addAll(arr);

        while(!queue.isEmpty()){
            int weight = queue.poll();
            for(int i=0; i<mapWeights.get(weight); i++){
                subsetA.add(weight);
                subsetB.remove(subsetB.indexOf(weight));
                subsetBWeights -= weight;
                subsetAWeights += weight;
            }
            if(subsetA.size() < subsetB.size() && subsetAWeights > subsetBWeights){
                result = subsetA;
            }
            if(subsetA.size() >= subsetB.size()){
                break;
            }
        }

        return result;

    }

}
