package algorithms.string.anagram;

import java.util.*;
import org.testng.annotations.Test;

/****
 * https://leetcode.com/problems/group-anagrams/
 * 
 * Given an array of strings strs, group the anagrams together. You can return
 * the answer in any order.
 * 
 * An Anagram is a word or phrase formed by rearranging the letters of a
 * different word or phrase, typically using all the original letters exactly
 * once.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: strs = ["eat","tea","tan","ate","nat","bat"] Output:
 * [["bat"],["nat","tan"],["ate","eat","tea"]] Example 2:
 * 
 * Input: strs = [""] Output: [[""]] Example 3:
 * 
 * Input: strs = ["a"] Output: [["a"]]
 * 
 * 
 * Constraints:
 * 
 * 1 <= strs.length <= 104 0 <= strs[i].length <= 100 strs[i] consists of
 * lower-case English letters.
 * 
 */

public class Anagram03_GroupAnagrams {

	@Test
	private void test() {
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
		System.out.println(groupAnagrams(strs));

	}

	/**
	 * Time Complexity: O(NKlogK), where N is the length of strs,
	 * 	and K is the maximum length of a string in strs.
	 * 	The outer loop has complexity O(N) as we iterate through each string.
	 * 	Then, we sort each string in O(KlogK) time.
	 *
	 * Space Complexity: O(NK), the total information content stored in ans.
	 */

	public List<List<String>> groupAnagrams(String[] strs) {

		List<List<String>> result = new ArrayList<>();
		HashMap<String, List<String>> map = new HashMap<>();

		for (String word : strs) {
			char[] current = word.toCharArray();
			Arrays.sort(current);
			String sorted = new String(current);
			List<String> anagrams = map.getOrDefault(sorted, new ArrayList<>());
			anagrams.add(word);
			map.put(sorted, anagrams);
		}
		result.addAll(map.values());
		return result;
	}
}
