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

		Map<Character, Integer> map = new HashMap<>();

		// Get the last index the character.
		for (int i = 0; i < S.length(); i++) {
			map.put(S.charAt(i), i);
		}

		int last = 0;
		int start = 0;

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < S.length(); i++) {

			last = Math.max(last, map.get(S.charAt(i))); //***** MATH.MAX is core logic *****/
			if (last == i) {
				list.add(last - start + 1);
				start = last + 1;
			}
		}

		return list;
	}

}
