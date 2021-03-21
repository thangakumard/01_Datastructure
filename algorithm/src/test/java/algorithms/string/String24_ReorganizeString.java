package algorithms.string;

import org.testng.annotations.Test;
import java.util.*;

/****
 * 
 * https://leetcode.com/problems/reorganize-string/
 * 
 * Given a string S, check if the letters can be rearranged so that two
 * characters that are adjacent to each other are not the same.
 * 
 * If possible, output any possible result. If not possible, return the empty
 * string.
 * 
 * Example 1:
 * 
 * Input: S = "aab" Output: "aba" Example 2:
 * 
 * Input: S = "aaab" Output: "" Note:
 * 
 * S will consist of lowercase letters and have length in range [1, 500].
 * 
 */
public class String24_ReorganizeString {
	
	@Test
	private void test() {
		
		System.out.println(reorganizeString("aaaabbbbbbbbbbbbbbbb"));
	}

	public String reorganizeString(String s) {
		if (s == null || s.isEmpty())
			return "";
		HashMap<Character, Integer> charMap = new HashMap<Character, Integer>();
		for (char c : s.toCharArray()) {
			charMap.put(c, charMap.getOrDefault(c, 0) + 1);
		}

		PriorityQueue<Character> maxHeap = new PriorityQueue<Character>((a, b) -> charMap.get(b) - charMap.get(a));
		maxHeap.addAll(charMap.keySet());
		StringBuilder sb = new StringBuilder();

		while (maxHeap.size() > 1) {

			char first = maxHeap.remove();
			char next = maxHeap.remove();

			sb.append(first);
			sb.append(next);

			charMap.put(first, charMap.get(first) - 1);
			charMap.put(next, charMap.get(next) - 1);

			if (charMap.get(first) > 0) {
				maxHeap.add(first);
			}
			if (charMap.get(next) > 0) {
				maxHeap.add(next);
			}
		}

		if (maxHeap.size() > 0) {
			char last = maxHeap.remove();
			if (charMap.get(last) > 1)
				return "";
			else
				sb.append(last);
		}

		return sb.toString();
	}
}
