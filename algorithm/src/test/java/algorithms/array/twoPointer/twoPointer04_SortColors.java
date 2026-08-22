package algorithms.array.medium.twoPointer;

/**
 * https://leetcode.com/problems/sort-colors/description/
 * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
 * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
 * You must solve this problem without using the library's sort function.
 *
 * Example 1:
 * Input: nums = [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 *
 * Example 2:
 * Input: nums = [2,0,1]
 * Output: [0,1,2]
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] is either 0, 1, or 2.
 *
 * Follow up: Could you come up with a one-pass algorithm using only constant extra space?
 */
public class twoPointer04_SortColors {
    public void sortColors(int[] nums) {
        int left = 0, right = nums.length-1, current = 0, temp = 0;
        while (current <= right) {
            if (nums[current] == 0) {
                temp = nums[left];
                nums[left] = nums[current];
                nums[current] = temp;
                left++;
                current++;
            }
            else if (nums[current] == 2) {
                temp = nums[current];
                nums[current] = nums[right];
                nums[right] = temp;
                right--;
            }else{
                current++;
            }
        }
    }
}
