package algorithms.array.medium.binarySearch;

/**
 * Meta Interview Quest: Imagine an array of numbers where every number occurs twice.
 * However, one number appears only once. How would you find this number in O(log n) time?
 *
 * Approach:
 * Binary Search:
 *          Since the numbers appear twice except for one unique number, the array can be divided into pairs. For a valid pair, the first occurrence will be at an even index, and the second occurrence will be at the next odd index.
 * Odd/Even Index Check:
 *          During the binary search, we need to check the mid index:
 *          If mid is even and the next element is the same as mid, the unique element must be in the second half.
 **         If mid is odd and the previous element is the same as mid, the unique element must be in the second half.
 *          Otherwise, the unique element is in the first half.
 * Update the Search Range:
 *          Adjust the search range based on the checks until the unique element is found.
 */
public class BinarySearch11_UniqueElementFinder {
    public int findUnique(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // Ensure mid is even
            if (mid % 2 == 1) {
                mid--;
            }
            // Check if the next element is the same as mid
            if (nums[mid] == nums[mid + 1]) {
                left = mid + 2; // Unique element is in the second half
            } else {
                right = mid; // Unique element is in the first half
            }
        }
        return nums[left];
    }
}
