package algorithms.string.search;

import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;


/*******
 * https://leetcode.com/problems/implement-strstr/
 * 
 * Implement strStr().

	Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
	
	Example 1:
	
	Input: haystack = "hello", needle = "ll"
	Output: 2
	Example 2:
	
	Input: haystack = "aaaaa", needle = "bba"
	Output: -1
	Clarification:
	
	What should we return when needle is an empty string? This is a great question to ask during an interview.
	
	For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
 *
 */
public class String03_Haystack_Needle {

	@Test
	public void testString(){
		Assertions.assertThat(approach_01("heeeoll", "ll")).isEqualTo(5);
	}
	public int approach_01(String haystack, String needle) {
		  for (int i = 0; ; i++) {
		    for (int j = 0; ; j++) {
		      if (j == needle.length()) return i;
		      if (i + j == haystack.length()) return -1;
		      if (needle.charAt(j) != haystack.charAt(i + j)) break;
		    }
		  }
		}
	
	/*
	 * Time complexity: \mathcal{O}((N - L)L)O((N−L)L), 
	 * where N is a length of haystack and L is a length of needle.
	 * We compute a substring of length L in a loop, which is executed (N - L) times.

	  Space complexity: \mathcal{O}(1)O(1).
	 */
	public int approach_02(String haystack, String needle) {
	    int L = needle.length(), n = haystack.length();

	    for (int start = 0; start < n - L + 1; ++start) {
	      if (haystack.substring(start, start + L).equals(needle)) {
	        return start;
	      }
	    }
	    return -1;
	  }
}
