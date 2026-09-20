package algorithms.priorityQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityQueue07_kSmallestPairs {
    /***
     * Time Complexity: O(K Log(min(K,N))
     * Space: O(min(K,N))
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k <= 0) {
            return result;
        }

        // Min-heap stores triplet arrays: {sum, index_in_nums1, index_in_nums2}
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[0], b[0])
        );

        // Initialize heap with at most min(k, nums1.length) elements from nums1, each paired with nums2[0]
        for (int i = 0; i < Math.min(nums1.length, k); i++) {
            minHeap.offer(new int[] { nums1[i] + nums2[0], i, 0 });
        }

        // Extract the k smallest pairs
        while (k > 0 && !minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int i = current[1];
            int j = current[2];

            result.add(Arrays.asList(nums1[i], nums2[j]));
            k--;

            // If there's a next element in nums2, push (nums1[i], nums2[j + 1]) to the heap
            if (j + 1 < nums2.length) {
                minHeap.offer(new int[] { nums1[i] + nums2[j + 1], i, j + 1 });
            }
        }

        return result;
    }
}
