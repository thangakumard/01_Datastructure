package algorithms.string;

import java.util.*;

import org.testng.annotations.Test;

public class String23_Partitionlabels {

	/****
	 * https://algorithmsandme.com/partition-labels/
	 * https://leetcode.com/problems/partition-labels/
	 * 
	 * Given a string S, partition the string into maximum possible partitions so
	 * that each letter appears in at most one part, and return a list of integers
	 * containing the size of each partition. For example:
	 * 
	 * Input: S = "ababcbacadefegdehijhklij" Output: [9,7,8] Explanation: The
	 * partition is "ababcbaca", "defegde", "hijhklij". This is a partition so that
	 * each letter appears in at most one part. A partition like "ababcbacadefegde",
	 * "hijhklij" is incorrect, because it splits S into fewer parts.
	 */

	@Test
	public void test() {
		System.out.println(partitionLabels("ababcbacadefegdehijhklij"));
	}

	public List<Integer> partitionLabels(String S) {
		Map<Character, Integer> lastOccurrenceMap = new HashMap<>();
		// Get the last index the character.
		for (int i = 0; i < S.length(); i++) {
			lastOccurrenceMap.put(S.charAt(i), i);
		}
		int max_lastOccurrence_index = -1;
		int start_Index = 0;
		List<Integer> partitionList = new ArrayList<>();
		for (int i = 0; i < S.length(); i++) {
			max_lastOccurrence_index = Math.max(max_lastOccurrence_index, lastOccurrenceMap.get(S.charAt(i))); //***** MATH.MAX is core logic *****/
			if (max_lastOccurrence_index == i) {
				partitionList.add(max_lastOccurrence_index - start_Index + 1);
				start_Index = max_lastOccurrence_index + 1; //** IMPORTANT
			}
		}
		return partitionList;
	}
}
