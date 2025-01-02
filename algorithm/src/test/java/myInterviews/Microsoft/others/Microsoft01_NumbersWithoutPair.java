package myInterviews.Microsoft.others;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * n the given unsorted array there are 2 number without pair.
 * Return the numbers without pair in the descending order.
 * Provide solution in Java
 */
public class Microsoft01_NumbersWithoutPair {
    public static int[] findUniqueNumbers(int[] nums) {
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
        //Arrays.sort(result);
        if (result[0] < result[1]) {
            int temp = result[0];
            result[0] = result[1];
            result[1] = temp;
        }

        return result;
    }

    @Test
    public void Test() {
        int[] nums = {4, 1, 2, 1, 2, 4, 5, 7};  // Example input
        int[] result = findUniqueNumbers(nums);
        System.out.println("Numbers without pairs in descending order: " + Arrays.toString(result));
    }
}
