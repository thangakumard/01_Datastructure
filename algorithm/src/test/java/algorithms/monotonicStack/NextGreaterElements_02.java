package algorithms.monotonicStack;

import java.util.Stack;

/**
 * https://leetcode.com/problems/next-greater-element-ii/
 * Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] is nums[0]), return the next greater number for every element in nums.
 *
 * The next greater number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, return -1 for this number.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,1]
 * Output: [2,-1,2]
 * Explanation: The first 1's next greater number is 2;
 * The number 2 can't find next greater number.
 * The second 1's next greater number needs to search circularly, which is also 2.
 * Example 2:
 *
 * Input: nums = [1,2,3,4,3]
 * Output: [2,3,4,-1,4]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 */
public class NextGreaterElements_02 {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int index = 0;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        //Iterate from right to left for the size of 2*n
        for(int i= 2*n-1; i >=0; i--){
            index = i%n;

            //Pop all the elements that are <= current, because that can NOT be next greater
            while(!stack.isEmpty() && stack.peek() <= nums[index]){
                stack.pop();
            }

            // Only fill results when i < n (first pass for original positions)
            if(i < n){
                result[i] = stack.isEmpty() ? -1: stack.peek();
            }

            // Push current as a candidate for earlier elements
            stack.push(nums[index]);
        }
        return result;
    }
}
