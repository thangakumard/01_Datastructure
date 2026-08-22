package algorithms.array.medium.counter;

/***
 * https://leetcode.com/problems/maximum-swap/
 * You are given an integer num. You can swap two digits at most once to get the maximum valued number.
 * Return the maximum valued number you can get.
 *
 * Example 1:
 * Input: num = 2736
 * Output: 7236
 * Explanation: Swap the number 2 and the number 7.
 *
 * Example 2:
 * Input: num = 9973
 * Output: 9973
 * Explanation: No swap.
 *
 * Constraints:
 * 0 <= num <= 108
 */
public class maximumSwap {
    /**
     * Time : O(n)
     * Space: O(1)
     */
    public int maximumSwap(int num) {
        int[] indexHolder = new int[10];
        char[] input = Integer.toString(num).toCharArray();


        for(int i=0; i < input.length ; i++){
            indexHolder[input[i] - '0'] = i;
        }

        for(int i=0; i < input.length ; i++){
            for(int j=9; j > input[i]-'0'; j--){
                if(indexHolder[j] > i){
                    char temp = input[i];
                    input[i] = input[indexHolder[j]];
                    input[indexHolder[j]] = temp;
                    return Integer.valueOf(new String(input));
                }
            }
        }

        return num;
    }
}
