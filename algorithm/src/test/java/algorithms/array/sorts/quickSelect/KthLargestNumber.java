package algorithms.array.sorts.quickSelect;

import java.util.PriorityQueue;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
/*
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 * 
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

	Example 1:
	
	Input: [3,2,1,5,6,4] and k = 2
	Output: 5
	Example 2:
	
	Input: [3,2,3,1,2,4,5,5,6] and k = 4
	Output: 4
	Note:
	You may assume k is always valid, 1 ≤ k ≤ array's length.


 */

public class KthLargestNumber {
	@Test
	public void test() {
		int[] input = { 5,2,4,1,3,6,0};
		Assertions.assertThat(findKthLargest_big_o_nlogK(input, 4)).isEqualTo(3);
		Assertions.assertThat(findKthLargest_big_o_nlogn(input, 4)).isEqualTo(3);
		int a = 10;
	}

	int result = -1;

	/*
	 * QuickSort vs QuickSelect
	 * -------------------------
	 * Yes, they're different algorithms — but closely related, since QuickSelect
	 * is essentially built *from* QuickSort's core idea (partitioning).
	 *
	 * Core Difference: What They Solve
	 * ---------------------------------------------------------------------
	 * |             | QuickSort                  | QuickSelect                    |
	 * |-------------|-----------------------------|---------------------------------|
	 * | Goal        | Sort the *entire* array     | Find the k-th smallest/largest |
	 * | Output      | Fully sorted array          | A single element (its final    |
	 * |             |                             | position)                      |
	 * | Recursion   | Recurses into **both**      | Recurses into **only one**     |
	 * |             | partitions                  | partition (the side with k)    |
	 * ---------------------------------------------------------------------
	 *
	 * How They're Related
	 * --------------------
	 * Both use the same partition step (Lomuto/Hoare): pick a pivot, rearrange
	 * so smaller elements go left and larger go right, and the pivot lands in
	 * its final sorted position.
	 *
	 *  - QuickSort: "Now recursively sort *both* the left and right subarrays."
	 *  - QuickSelect: "I only care about the k-th element. After partitioning,
	 *      check where the pivot landed:
	 *        - pivot's index == k        -> done, return it.
	 *        - k < pivot's index         -> recurse into the LEFT subarray only.
	 *        - k > pivot's index         -> recurse into the RIGHT subarray only."
	 *
	 * This "only recurse one side" difference is what changes the complexity:
	 * ---------------------------------------------------------------------
	 * |                  | QuickSort       | QuickSelect                  |
	 * |------------------|-----------------|-------------------------------|
	 * | Best/Avg Time    | O(n log n)      | O(n)                          |
	 * | Worst Time       | O(n^2)          | O(n^2)                        |
	 * | Space (recursive)| O(log n) avg    | O(log n) avg, O(n) worst      |
	 * ---------------------------------------------------------------------
	 *
	 * Intuition for Why QuickSelect Is Faster (O(n) vs O(n log n))
	 * --------------------------------------------------------------
	 * QuickSort does O(n) work at *every level* of recursion, and there are
	 * O(log n) levels -> O(n log n) total.
	 *
	 * QuickSelect also does O(n) work at the first level, but then only
	 * recurses into ONE shrinking subarray -- so the total work forms a
	 * geometric series (n + n/2 + n/4 + ... ~ 2n) rather than n accumulated
	 * across O(log n) *full* levels. That's why it collapses to O(n) instead
	 * of O(n log n).
	 *
	 * Quick Analogy
	 * -------------
	 *  - QuickSort   = "organize my entire bookshelf alphabetically."
	 *  - QuickSelect = "just tell me which book is the 7th one alphabetically"
	 *      -- you don't need to fully sort the shelf to answer that; you can
	 *      discard half the books at each step without ever sorting them.
	 */
	public int findKthLargest_big_o_nlogn(int[] nums, int k) {
		/** IMPORTANT ** As we need Kth Largest, we need to sort he array in descending order ***/
		int result = quickSort(nums, 0, nums.length - 1, k);
		return result;
	}

	//SAMPLE INPUT: 6,2,7,8,1,9
	//While coding keep-in mind small numbers will move to the right
	private int quickSort(int[] input, int left, int right, int k) {
		int pivot = input[left];
		int initial_left = left;
		int initial_right = right;
		while (left < right) {
			while (pivot >= input[right] && left < right) { //To be in descending order
				//right side value is < than pivot. So we are good. Move the right pointer--
				right--;
			}
			if (left != right) {
				//move the bigger value in the right to the left
				input[left] = input[right];
				left++;
			}
			while (pivot <= input[left] && left < right) {//To be in descending order
				//Left side value is > than pivot. So we are good. Move left++
				left++;
			}
			if (left != right) {
				input[right] = input[left];
				right--;
			}
		}

		input[left] = pivot;
		int pivotIndex = left;
		int result = 0;
        if(k-1 == pivotIndex)
            return input[pivotIndex];
        if(k-1 <= pivotIndex-1 && initial_left <= pivotIndex-1){
            result = quickSort(input, initial_left,pivotIndex-1,k);
        }
        else if(k-1 >= pivotIndex+1 && pivotIndex+1 <= initial_right){
            result = quickSort(input,pivotIndex+1,initial_right,k);
        }
        
        return result;
	}

	/**
	 * Time complexity O(n⋅logk)
	 * n is the size of array
	 * heap is limited to a size of k
	 * @param nums
	 * @param k
	 * @return
	 */
    public int findKthLargest_big_o_nlogK(int[] nums, int k) {
        PriorityQueue<Integer> minQueue = new PriorityQueue<>();
        
        for(int i=0; i < nums.length; i++){
            minQueue.add(nums[i]);
            if(minQueue.size() > k){
                minQueue.remove();
            }
        }
        
        return minQueue.remove();
    }
}
