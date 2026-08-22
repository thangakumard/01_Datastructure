package algorithms.array.medium.subArray;

/**
 * https://www.metacareers.com/profile/coding_practice_question/?problem_id=226517205173943&c=1344573719835068&psid=275492097255885&practice_plan=0&b=0222222
 * Contiguous Subarrays
 * You are given an array arr of N integers. For each index i, you are required to determine the number of contiguous subarrays that fulfill the following conditions:
 * The value at index i must be the maximum element in the contiguous subarrays, and
 * These contiguous subarrays must either start from or end on index i.
 * Signature
 * int[] countSubarrays(int[] arr)
 * Input
 * Array arr is a non-empty list of unique integers that range between 1 to 1,000,000,000
 * Size N is between 1 and 1,000,000
 * Output
 * An array where each index i contains an integer denoting the maximum number of contiguous subarrays of arr[i]
 * Example:
 * arr = [3, 4, 1, 6, 2]
 * output = [1, 3, 1, 5, 1]
 * Explanation:
 * For index 0 - [3] is the only contiguous subarray that starts (or ends) at index 0 with the maximum value in the subarray being 3.
 * For index 1 - [4], [3, 4], [4, 1]
 * For index 2 - [1]
 * For index 3 - [6], [6, 2], [1, 6], [4, 1, 6], [3, 4, 1, 6]
 * For index 4 - [2]
 * So, the answer for the above input is [1, 3, 1, 5, 1]
 */
public class Subarray05_countSubarrays {
    private int leftSubArrayCount(int[] input, int left, int right){
        int counter = 0;
        while(left >=0 && input[left] < input[right]){
            counter++;
            left--;
        }
        return counter;
    }
    private int rightSubArrayCount(int[] input, int left, int right){
        int counter = 0;
        while(right < input.length && input[right] < input[left]){
            counter++;
            right++;
        }
        return counter;
    }
    int[] countSubarrays(int[] arr) {
        // Write your code here
        int[] result = new int[arr.length];
        for(int i=0; i < arr.length; i++)
        {
            result[i] = 1 +   leftSubArrayCount(arr, i-1,i) + rightSubArrayCount(arr, i, i + 1);
        }

        return result;
    }
}
