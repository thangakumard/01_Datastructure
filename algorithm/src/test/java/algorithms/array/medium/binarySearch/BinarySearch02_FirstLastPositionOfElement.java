package algorithms.array.medium.binarySearch;

import org.testng.annotations.Test;

public class BinarySearch02_FirstLastPositionOfElement {
    @Test
    public void test() {

        int[] input = { 5, 7, 7, 8, 8, 10 };
        int[] result = searchRange(input, 8);
        System.out.println("First Inde :" + result[0]);
        System.out.println("Last Inde :" + result[1]);
    }

    /*
     * Time complexity O(logn) Space complexity O(1)
     */

    public int[] searchRange(int[] nums, int target) {
        int[] result = { -1, -1 };

        if (nums.length < 1)
            return result;

        result[0] = leftMostPositionOfElement(nums, target);
        result[1] = rightMostPositionOfElement(nums, target);

        return result;
    }

    private int leftMostPositionOfElement(int[] nums, int target) {
        int targetMatchingIndex = -1;
        int start = 0, end = nums.length - 1, mid = 0;

        while (start <= end) {
            mid = start + (end - start) / 2;

            if (nums[mid] >= target) { //Even if the value matches, keep moving to the left
                end = mid - 1;
            } else {
                start = mid + 1;
            }

            //**IMPORTANT - Keep track of the target matching index. But do not return here.
            if (nums[mid] == target)
                targetMatchingIndex = mid;
        }

        return targetMatchingIndex;
    }

    private int rightMostPositionOfElement(int[] nums, int target) {
        int targetMatchingIndex = -1;
        int start = 0, end = nums.length - 1, mid = 0;

        while (start <= end) {
            mid = start + (end - start) / 2;

            if (nums[mid] <= target) {//Even if the value matches, keep moving to the right
                start = mid + 1;
            } else {
                end = mid - 1;
            }

            if (nums[mid] == target)
                targetMatchingIndex = mid;
        }

        return targetMatchingIndex;
    }

}
