package arrayAlgorithms;

import org.testng.annotations.Test;

/******
 * 
 * https://leetcode.com/problems/palindromic-substrings/description/
 * Given a string, your task is to count how many palindromic substrings in this string.

	The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.
	
	Example 1:
	Input: "abc"
	Output: 3
	Explanation: Three palindromic strings: "a", "b", "c".
	Example 2:
	Input: "aaa"
	Output: 6
	Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 *
 */
public class Palindrome04_Palindromic_Substring {
	@Test
	public void Test()
	{
		System.out.println("Number of substring :" + countSubstrings("aca"));
	}
	
	public int countSubstrings(String S) {
        int N = S.length(), ans = 0;
        for (int center = 0; center <= (2*N)-1; ++center) {
            int left = center / 2;
            int right = left + (center % 2);
            while (left >= 0 && right < N && S.charAt(left) == S.charAt(right)) {
                ans++;
                left--;
                right++;
            }
        }
        return ans;
    }
}
