package algorithms.array.medium;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Array10_TopKFrequent {

    @Test
    public void topKFrequentTest(){
        int[] nums = {1,1,1,2,2,3}; int k = 2;
        int[] result = {1,2};
        Assertions.assertThat(topKFrequent(nums,k)).isEqualTo(result);
    }
    public int[] topKFrequent(int[] nums, int k) {
        int[] result = new int[k];
        HashMap<Integer, Integer> mapCounter = new HashMap<>();

        for(int i: nums){
            mapCounter.put(i , mapCounter.getOrDefault(i, 0)+1);
        }

        List<Integer> lstKeys = new ArrayList<>(mapCounter.keySet());
        Collections.sort(lstKeys, (a, b) -> mapCounter.get(b) - mapCounter.get(a));

        for(int i=0; i < k; i++){
            result[i] = lstKeys.get(i);
        }

        return result;

    }
}
