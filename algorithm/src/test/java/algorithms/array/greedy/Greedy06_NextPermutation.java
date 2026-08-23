package algorithms.array.greedy;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/next-permutation/
 * A permutation of an array of integers is an arrangement of its members into a sequence or linear order.
 *
 * For example, for arr = [1,2,3], the following are considered permutations of arr: [1,2,3], [1,3,2], [3,1,2], [2,3,1].
 * The next permutation of an array of integers is the next lexicographically greater permutation of its integer.
 * More formally, if all the permutations of the array are sorted in one container according to their lexicographical order, then the next permutation of that array is the permutation that follows it in the sorted container. If such arrangement is not possible, the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).
 *
 * For example, the next permutation of arr = [1,2,3] is [1,3,2].
 * Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
 * While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does not have a lexicographical larger rearrangement.
 * Given an array of integers nums, find the next permutation of nums.
 *
 * The replacement must be in place and use only constant extra memory.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [1,3,2] ===> 132 is the next possible larger number
 *
 * Example 2:
 * Input: nums = [3,2,1]
 * Output: [1,2,3] ===> There is no larger number than 321. So reverse the input
 *
 * Example 3:
 * Input: nums = [1,1,5]
 * Output: [1,5,1] ===> 151 is the next possible larger number
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 * Accepted
 * 892,384
 * Submissions
 */
public class Greedy06_NextPermutation {

    @Test
    public void nextPermutationTest(){
        int[] input = new int[]{1,2,3};
        nextPermutation(input);
        Assertions.assertThat(input).containsExactly(new int[]{1,3,2});

        input = new int[]{1,3,2};
        nextPermutation(input);
        Assertions.assertThat(input).containsExactly(new int[]{2,1,3});

    }

    /***
     * Time: O(n)
     * Space: O(1) - Everything happens in place using only the index variables i and j.
     * No auxiliary arrays, no recursion stack.
     * This matches the problem's explicit constraint to do it in-place.
     */
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;

        // Step 1: find rightmost smallest number
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Step 2: find rightmost next larger number than nums[i]
            int j = n - 1; // Start from the right
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        // Step 3: reverse suffix after i (works even if i == -1, reverses whole array)
        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}
