package myInterviews;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * In the given unsorted array there are 2 number without pair.
 * Return the numbers without pair in the descending order.
 * Provide solution in Java
 */
public class Microsoft02_findUniqueNumbers {

    @Test
    public void findUniqueNumbers_test() {
        int[] nums = {4, 1, 2, 1, 2, 4, 5, 7};  // Example input
        int[] result = findUniqueNumbers_usingxor(nums);
        System.out.println("Numbers without pairs in descending order: " + Arrays.toString(result));

        result = findUniqueNumbers_HashSet(nums);
        System.out.println("Numbers without pairs in descending order: " + Arrays.toString(result));
    }

    public int[] findUniqueNumbers_usingxor(int[] nums) {
        int xorResult = 0;

        // Step 1: XOR all numbers to find xorResult of the two unique numbers
        for (int num : nums) {
            xorResult ^= num;
        }

        // Step 2: Find the rightmost set bit in xorResult
        int rightmostSetBit = xorResult & -xorResult;

        // Step 3: Use the rightmost set bit to partition the numbers into two groups
        int unique1 = 0, unique2 = 0;
        for (int num : nums) {
            if ((num & rightmostSetBit) != 0) {
                unique1 ^= num;
            } else {
                unique2 ^= num;
            }
        }

        // Step 4: Return the two unique numbers in descending order
        int[] result = new int[2];
        if (unique1 > unique2) {
            result[0] = unique1;
            result[1] = unique2;
        } else {
            result[0] = unique2;
            result[1] = unique1;
        }

        return result;
    }

    public static int[] findUniqueNumbers_HashSet(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        // Step 1: Add/remove numbers from the HashSet
        for (int num : nums) {
            if (set.contains(num)) {
                set.remove(num);  // Remove if the number appears again (it has a pair)
            } else {
                set.add(num);     // Add if the number appears only once so far
            }
        }

        // Step 2: Convert HashSet to array
        int[] result = new int[2];
        int i = 0;
        for (int num : set) {
            result[i++] = num;
        }

        // Step 3: Sort in descending order
        Arrays.sort(result);
        if (result[0] < result[1]) {
            int temp = result[0];
            result[0] = result[1];
            result[1] = temp;
        }

        return result;
    }

}
