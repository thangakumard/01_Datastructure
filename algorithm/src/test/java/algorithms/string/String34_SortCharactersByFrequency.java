package algorithms.string;

import java.util.*;

/****
 * https://leetcode.com/problems/sort-characters-by-frequency/
 * 
 * Given a string, sort it in decreasing order based on the frequency of
 * characters.
 * 
 * Example 1:
 * 
 * Input: "tree"
 * 
 * Output: "eert"
 * 
 * Explanation: 'e' appears twice while 'r' and 't' both appear once. So 'e'
 * must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
 * Example 2:
 * 
 * Input: "cccaaa"
 * 
 * Output: "cccaaa"
 * 
 * Explanation: Both 'c' and 'a' appear three times, so "aaaccc" is also a valid
 * answer. Note that "cacaca" is incorrect, as the same characters must be
 * together. Example 3:
 * 
 * Input: "Aabb"
 * 
 * Output: "bbAa"
 * 
 * Explanation: "bbaA" is also a valid answer, but "Aabb" is incorrect. Note
 * that 'A' and 'a' are treated as two different characters.
 *
 */

public class String34_SortCharactersByFrequency {

	public String frequencySort(String s) {

		HashMap<Character, Integer> charMap = new HashMap<Character, Integer>();
		for (char c : s.toCharArray()) {
			charMap.put(c, charMap.getOrDefault(c, 0) + 1);
		}

//		PriorityQueue<Character> maxQueue = new PriorityQueue<Character>((a, b) -> charMap.get(b) - charMap.get(a));
//
//		maxQueue.addAll(charMap.keySet());
//		StringBuilder sb = new StringBuilder();
//		while (!maxQueue.isEmpty()) {
//			char c = maxQueue.remove();
//			int count = charMap.get(c);
//			for (int i = 0; i < count; i++) {
//				sb.append(c + "");
//			}
//		}

		List<Character> lstChar = new ArrayList<>(charMap.keySet());
		Collections.sort(lstChar, (a,b) -> charMap.get(b) - charMap.get(a));

		StringBuilder sb = new StringBuilder();
		for(char c: lstChar){
			int count = charMap.get(c);
			while(count > 0){
				sb.append(c);
				count--;
			}
		}

		return sb.toString();
	}

}
