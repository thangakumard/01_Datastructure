package myInterviews;

import java.util.HashMap;

/**
 * https://www.geeksforgeeks.org/find-subarray-with-given-sum-in-array-of-integers/
 * Given an unsorted array of integers, find a subarray that adds to a given number. If there is more than one subarray with the sum of the given number, print any of them.
 *
 * Examples:
 * Input: arr[] = {1, 4, 20, 3, 10, 5}, sum = 33
 * Output: Sum found between indexes 2 and 4
 * Explanation: Sum of elements between indices 2 and 4 is 20 + 3 + 10 = 33
 *
 * Input: arr[] = {2, 12, -2, -20, 10}, sum = -10
 * Output: Sum found between indexes 1 to 3
 * Explanation: Sum of elements between indices 0 and 3 is 12 – 2 – 20 = -10
 *
 * Input: arr[] = {-10, 0, 2, -2, -20, 10}, sum = 20
 * Output: No subarray with given sum exists
 * Explanation: There is no subarray with the given sum
 */
public class Tiktok_TargetsumwithNegativeValue {
    public static void subArraySum(int[] arr, int n, int sum)
    {
        // cur_sum to keep track of cumulative sum till that
        // point
        int cur_sum = 0;
        int start = 0;
        int end = -1;
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            cur_sum = cur_sum + arr[i];
            // check whether cur_sum - sum = 0, if 0 it
            // means the sub array is starting from index 0-
            // so stop
            if (cur_sum - sum == 0) {
                start = 0;
                end = i;
                break;
            }
            // if hashMap already has the value, means we
            // already
            // have subarray with the sum - so stop
            if (hashMap.containsKey(cur_sum - sum)) {
                start = hashMap.get(cur_sum - sum) + 1;
                end = i;
                break;
            }
            // if value is not present then add to hashmap
            hashMap.put(cur_sum, i);
        }
        // if end is -1 : means we have reached end without
        // the sum
        if (end == -1) {
            System.out.println(
                    "No subarray with given sum exists");
        }
        else {
            System.out.println("Sum found between indexes "
                    + start + " to " + end);
        }
    }

    /***
     * another version of this question
     * https://www.geeksforgeeks.org/problems/subarray-range-with-given-sum0128/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
     * Given an unsorted array of integers and a sum. The task is to count the number of subarray which adds to the given sum.
     *
     * Example 1:
     *
     * Input:
     * n = 5
     * arr[] = {10,2,-2,-20,10}
     * sum = -10
     * Output: 3
     * Explanation: Subarrays with sum -10 are: [10, 2, -2, -20], [2, -2, -20, 10] and [-20, 10].
     * Example 2:
     *
     * Input:
     * n = 6
     * arr[] = {1,4,20,3,10,5}
     * sum = 33
     * Output: 1
     * Explanation: Subarray with sum 33 is: [20,3,10].
     * Your Task:
     * This is a function problem. You only need to complete the function subArraySum() that takes an integer array arr and 2 integers n and sum as parameters and returns an integer denoting the count of subarrays which add up to the given sum.
     *
     * Expected Time Comlexity: O(n)
     * Expected Auxilary Space: O(n)
     *
     * Constraints:
     * 1 <= n <= 105
     * -105 <= arr[i] <= 105
     * -105 <= sum <= 105
     */

    public int subArraySum_02(int[] arr, int n, int sum)
    {
        HashMap<Integer,Integer> sumMap = new HashMap<>();
        sumMap.put(0, 1);
        int counter = 0;
        int presum = 0;
        for(int i=0; i < n; i++){
            presum += arr[i];
            if(sumMap.containsKey(presum-sum))
            {
                counter += sumMap.get(presum-sum);
            }
            sumMap.put(presum, sumMap.getOrDefault(presum,0)+1);
        }
        return counter;
    }
}
