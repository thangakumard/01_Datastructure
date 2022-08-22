package algorithms.priorityQueue;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.PriorityQueue;

public class PriorityQueue01_findLeastNumOfUniqueInts {
    @Test
    private void test() {
        int[] input = new int[] {4,3,1,1,3,3,2};
        Assert.assertEquals(2,findLeastNumOfUniqueInts(input, 3));

    }

    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        HashMap<Integer, Integer> mapInput = new HashMap<Integer, Integer>();
        for(int i=0; i < arr.length; i++){
            int count = mapInput.getOrDefault(arr[i], 0) + 1;
            mapInput.put(arr[i], count);
        }

        PriorityQueue<Integer> minQueue = new PriorityQueue<Integer>((a, b) -> mapInput.get(a) - mapInput.get(b));
        minQueue.addAll(mapInput.keySet());

        for(int i=0; i < k; i++){
            int key = minQueue.remove();

            int count = mapInput.get(key) - 1;
            if(count == 0){
                mapInput.remove(key);
            }else{
                mapInput.put(key,count);
                minQueue.add(key);
            }
        }

        return mapInput.size();
    }
}
