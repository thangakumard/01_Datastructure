package algorithms.array.medium.binarySearch;

import org.assertj.core.api.Assertions;
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

public class BinarySearch07_FindSmallestLetterGreaterThanTarget {

	@Test
	public void test() {
		Assertions.assertThat(searchNextLetter(new char[] { 'c', 'f', 'j' }, 'j')).isEqualTo('j');

		Assertions.assertThat(searchNextLetter(new char[] { 'c', 'f', 'h' }, 'd')).isEqualTo('f');
		Assertions.assertThat(searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'f')).isEqualTo('f');
		Assertions.assertThat(searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'b')).isEqualTo('c');
		Assertions.assertThat(searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'm')).isEqualTo('h');
		Assertions.assertThat(searchNextLetter(new char[] { 'a', 'c', 'f', 'h' }, 'h')).isEqualTo('h');
	}

	public char searchNextLetter(char[] letters, char target) {
		int left = 0, right = letters.length-1, mid = 0, closerToTarget = 0;
		while(left <= right){
			mid = left + (right - left)/2;
			if(letters[mid] <= target){
				left = mid+1;
			}else{
				closerToTarget = mid;
				right = mid-1;
			}
		}

		return letters[closerToTarget];
	}
}
