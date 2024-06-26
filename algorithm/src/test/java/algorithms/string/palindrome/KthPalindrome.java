package algorithms.string.palindrome;

/**
 * https://leetcode.com/problems/find-palindrome-with-fixed-length/description/
 * Given an integer array queries and a positive integer intLength, return an array answer where answer[i] is either the queries[i]th smallest positive palindrome of length intLength or -1 if no such palindrome exists.
 * A palindrome is a number that reads the same backwards and forwards. Palindromes cannot have leading zeros.
 *
 * Example 1:
 * Input: queries = [1,2,3,4,5,90], intLength = 3
 * Output: [101,111,121,131,141,999]
 * Explanation:
 * The first few palindromes of length 3 are:
 * 101, 111, 121, 131, 141, 151, 161, 171, 181, 191, 202, ...
 * The 90th palindrome of length 3 is 999.
 *
 * Example 2:
 * Input: queries = [2,4,6], intLength = 4
 * Output: [1111,1331,1551]
 * Explanation:
 * The first six palindromes of length 4 are:
 * 1001, 1111, 1221, 1331, 1441, and 1551.
 *
 * Constraints:
 * 1 <= queries.length <= 5 * 104
 * 1 <= queries[i] <= 109
 * 1 <= intLength <= 15
 */
public class KthPalindrome {
        public long[] kthPalindrome(int[] queries, int intLength) {
            int n=queries.length;
            long ans[]=new long[n];
            int ans_i=0;

            //in length intLength only (intLength+1)/2 palindromes present

            //e.g. in 4 length (range between 1000 to 9999) only (90 substring present)
            //as in given example 1001, 1111, 1221, 1331, 1441, 1551,...
            //as we can see it has 10, 11, 12, 13, 14, 15 (only 4/2 length or (4+1)/2)

            //e.g. in 3 length (range between 100 to 999) only (90 substring present)
            //as in given example 101, 111, 121, 131, 141, 151,...
            //as we can see it has 10, 11, 12, 13, 14, 15 (only (3+1)/2 length)

            //bcz palindrome is made of left substring mirror to right substring
            //so we care about first half and we can made second half from first half easily

            // intLength is 4 or 3 we get palindrome_present=2 (we care about)
            int palindrome_present = ( intLength+1 )/2 ;

            long l=(long)Math.pow(10,palindrome_present-1); //10
            long r=(long)Math.pow(10,palindrome_present)-1; //99


            for(int q:queries){

                //if queries[ith] is within the bound
                //r-l+1 because 10 to 99 we have all the palindrome (in total we have 99-10 +1)
                if(q<=(r-l+1)){

                    //first half is the minimum value in range (which is l) + query number -1
                    //-1 bcz we have l (10) number palindrome also
                    String left_half=Long.toString(l+q-1);

                    //second half is just mirror image (01)
                    String right_half=(new StringBuilder(left_half)).reverse().toString();

                    //now for intLength 4 we have (1001) and 3 we have (1001)
                    //we don't need middle value (right half 0 index) if intLength is odd
                    ans[ans_i]=Long.parseLong( left_half+right_half.substring(intLength % 2 ) );

                }
                else{
                    ans[ans_i]=-1;
                }

                ans_i++;
            }

            return ans;
        }
}
