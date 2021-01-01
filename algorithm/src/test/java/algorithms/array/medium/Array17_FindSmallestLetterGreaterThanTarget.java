package algorithms.array.medium;

import java.util.*;

import org.testng.annotations.Test;
/*
 * https://leetcode.com/problems/find-smallest-letter-greater-than-target/
 * Given a list of sorted characters letters containing only lowercase letters, 
 * and given a target letter target, 
 * find the smallest element in the list that is larger than the given target.

Letters also wrap around. For example, if the target is target = 'z' and letters = ['a', 'b'], the answer is 'a'.

Examples:
Input:
letters = ["c", "f", "j"]
target = "a"
Output: "c"

Input:
letters = ["c", "f", "j"]
target = "c"
Output: "f"

Input:
letters = ["c", "f", "j"]
target = "d"
Output: "f"

Input:
letters = ["c", "f", "j"]
target = "g"
Output: "j"

Input:
letters = ["c", "f", "j"]
target = "j"
Output: "c"

Input:
letters = ["c", "f", "j"]
target = "k"
Output: "c"
Note:
letters has a length in range [2, 10000].
letters consists of lowercase letters, and contains at least 2 unique letters.
target is a lowercase letter.
 * 
 */

public class Array17_FindSmallestLetterGreaterThanTarget {

	public char searchNextLetter(char[] letters, char key) {
		int start = 0, end = letters.length - 1;
		int n = letters.length;
		if (key < letters[0] || key > letters[n - 1])
			return letters[0];
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (letters[mid] > key) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}

		return letters[start % n];
	}

	@Test
	public void test() {
		System.out.println(this.searchNextLetter(new char[] { 'c', 'f', 'h' }, 'd'));
		System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'f'));
		System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'b'));
		System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'm'));
		System.out.println(this.searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'h'));
	}
}
