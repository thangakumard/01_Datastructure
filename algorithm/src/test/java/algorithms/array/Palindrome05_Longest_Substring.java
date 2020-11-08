package algorithms.array;

import org.testng.annotations.Test;

/*****************
 * 
 * https://www.geeksforgeeks.org/manachers-algorithm-linear-time-longest-palindromic-substring-part-1/
	Manacher�s Algorithm � Linear Time Longest Palindromic Substring � Part 1
	Given a string, find the longest substring which is palindrome.
	
	if the given string is �forgeeksskeegfor�, the output should be �geeksskeeg�
	if the given string is �abaaba�, the output should be �abaaba�
	if the given string is �abababa�, the output should be �abababa�
	if the given string is �abcbabcbabcba�, the output should be �abcbabcba�
 */

public class Palindrome05_Longest_Substring {
	@Test
	public void Test(){
		System.out.println(countSubstrings("abba"));
	}
	
	public int countSubstrings(String S) {
        char[] A = new char[2 * S.length() + 3];
        A[0] = '@';
        A[1] = '#';
        A[A.length - 1] = '$';
        int t = 2;
        for (char c: S.toCharArray()) {
            A[t++] = c;
            A[t++] = '#';
        }

        int[] Z = new int[A.length];
        int center = 0, right = 0;
        for (int i = 1; i < Z.length - 1; ++i) {
            if (i < right)
                Z[i] = Math.min(right - i, Z[2 * center - i]);
            while (A[i + Z[i] + 1] == A[i - Z[i] - 1])
                Z[i]++;
            if (i + Z[i] > right) {
                center = i;
                right = i + Z[i];
            }
        }
        int ans = 0;
        for (int v: Z) ans += (v + 1) / 2;
        return ans;
    }
}
