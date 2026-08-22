package algorithms.array.binarySearch;

/***
 * https://leetcode.com/problems/search-insert-position/
 * Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Example 1:
 * Input: nums = [1,3,5,6], target = 5
 * Output: 2

 * Example 2:
 * Input: nums = [1,3,5,6], target = 2
 * Output: 1

 * Example 3:
 * Input: nums = [1,3,5,6], target = 7
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums contains distinct values sorted in ascending order.
 * -104 <= target <= 104
 */
public class BinarySearch12_SearchInsert {
    /***
     * Time complexity : O(logN)
     * Space complexity: O(1)
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int middle;
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            middle = left + (right - left) / 2;
            if(nums[middle] == target) return middle;
            if(target < nums[middle]) {
                right = middle - 1;
            }
            else {
                left = middle + 1;
            }
        }
        return left;
    }
}
