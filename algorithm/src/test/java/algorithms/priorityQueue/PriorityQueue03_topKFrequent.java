package algorithms.priorityQueue;

import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 *
 * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class PriorityQueue03_topKFrequent {

    class Solution {

        /***
         * Bucket Sort algorithm
         * ===========
         * Time complexity : O(N)
         * Space Complexity: O(N)
         *
         * Use Bucket Sort when the maximum possible frequency (or range) is bounded
         * and predictable (like array lengths or string sizes).
         */
        public int[] topKFrequent_01(int[] nums, int k) {
            Map<Integer, Integer> mapCounter = new HashMap<>();
            for (int i : nums) {
                mapCounter.put(i, mapCounter.getOrDefault(i, 0) + 1);
            }

            // Create buckets where index = frequency
            List<Integer>[] bucket = new List[nums.length + 1];
            for (int key : mapCounter.keySet()) {
                int frequency = mapCounter.get(key);
                if (bucket[frequency] == null) {
                    bucket[frequency] = new ArrayList<>();
                }
                bucket[frequency].add(key);
            }

            // Gather the top K elements starting from the highest frequency
            int[] result = new int[k];
            int counter = 0;

            for (int pos = bucket.length - 1; pos >= 0 && counter < k; pos--) {
                if (bucket[pos] != null) {
                    for (int num : bucket[pos]) {
                        result[counter++] = num;
                        if (counter == k) break;
                    }
                }
            }
            return result;
        }
    }

    /***
     * Using Priority Queue
     * Time: O(N log K)
     * Space: O(N)
     *
     * Use a Heap when the range of frequencies or values is massive,
     * sparse, or unbounded, making an array index impossible or wasteful.
     */
    public int[] topKFrequent_02(int[] nums, int k) {
        // O(1) time
        if (k == nums.length) {
            return nums;
        }

        // 1. Build hash map: character and how often it appears
        // O(N) time
        Map<Integer, Integer> mapCounter = new HashMap();
        for (int n: nums) {
            mapCounter.put(n, mapCounter.getOrDefault(n, 0) + 1);
        }

        // init heap 'the less frequent element first'
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
                (n1, n2) -> mapCounter.get(n1) - mapCounter.get(n2));

        // 2. Keep k top frequent elements in the heap
        // O(N log k) < O(N log N) time
        for (int n: mapCounter.keySet()) {
            minHeap.add(n);
            if (minHeap.size() > k) minHeap.poll();
        }

        // 3. Build an output array
        // O(k log k) time
        int[] top = new int[k];
        for(int i = k - 1; i >= 0; --i) {
            top[i] = minHeap.poll();
        }
        return top;
    }
}
