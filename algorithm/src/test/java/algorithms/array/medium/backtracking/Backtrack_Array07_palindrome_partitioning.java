package algorithms.array.medium.backtracking;

import java.util.*;

import org.testng.annotations.Test;

/**
 * https://leetcode.com/problems/palindrome-partitioning/
 * https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
 * 
 * Given a string s, partition s such that every substring of the partition is a
 * palindrome. Return all possible palindrome partitioning of s.
 * 
 * A palindrome string is a string that reads the same backward as forward.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: s = "aab" Output: [["a","a","b"],["aa","b"]] Example 2:
 * 
 * Input: s = "a" Output: [["a"]]
 * 
 * 
 * Constraints:
 * 
 * 1 <= s.length <= 16 s contains only lowercase English letters.
 */
public class Backtrack_Array07_palindrome_partitioning {

	@Test
	private void test() {
		String input = "aaab";
		System.out.println(partition(input));
	}
	
	public List<List<String>> partition(String s) {
		List<List<String>> list = new ArrayList<>();
		backtrack(list, new ArrayList<>(), s, 0);
		return list;
	}

	public void backtrack(List<List<String>> list, List<String> tempList, String s, int start) {
		if (start == s.length())
			list.add(new ArrayList<>(tempList));
		else {
			for (int i = start; i < s.length(); i++) {
				if (isPalindrome(s, start, i)) {
					tempList.add(s.substring(start, i + 1));
					backtrack(list, tempList, s, i + 1);
					tempList.remove(tempList.size() - 1);
				} 
			}
		}
	}

	public boolean isPalindrome(String s, int low, int high) {
		while (low < high)
			if (s.charAt(low++) != s.charAt(high--))
				return false;
		return true;
	}
}
