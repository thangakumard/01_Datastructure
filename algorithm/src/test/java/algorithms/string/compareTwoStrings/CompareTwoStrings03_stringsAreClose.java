package algorithms.string.compareTwoStrings;

import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/***
 * https://leetcode.com/problems/determine-if-two-strings-are-close/
 * 
 * Two strings are considered close if you can attain one from the other using
 * the following operations:
 * 
 * Operation 1: Swap any two existing characters. For example, abcde -> aecdb
 * Operation 2: Transform every occurrence of one existing character into
 * another existing character, and do the same with the other character. For
 * example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)
 * You can use the operations on either string as many times as necessary.
 * 
 * Given two strings, word1 and word2, return true if word1 and word2 are close,
 * and false otherwise.
 * 
 * Input: word1 = "abc", word2 = "bca" Output: true Explanation: You can attain
 * word2 from word1 in 2 operations. Apply Operation 1: "abc" -> "acb" Apply
 * Operation 1: "acb" -> "bca"
 * 
 * 
 * Input: word1 = "cabbba", word2 = "abbccc" 
 * Output: true Explanation: You can
 * attain word2 from word1 in 3 operations. 
 * Apply Operation 1: "cabbba" -> "caabbb" 
 * Apply Operation 2: "caabbb" -> "baaccc" Apply Operation 2: "baaccc"
 * -> "abbccc"
 * 
 * 
 * Input: word1 = "cabbba", word2 = "aabbss" Output: false Explanation: It is
 * impossible to attain word2 from word1, or vice versa, in any amount of
 * operations.
 */

public class CompareTwoStrings03_stringsAreClose {

	@Test
	public void test() {
		Assert.assertTrue(isCloseString("abc", "bca"));
		Assert.assertTrue(isCloseString("cabbba", "abbccc"));
	}

	/****
	 * Need to check 2 conditions
	 * 1. Both strings should have common Alphabets . It's count may differ
	 * 2. The pattern of number of times a character appears should match
	 */

	public boolean isCloseString(String word1, String word2) {

		int N = 26;
		// count the English letters
		int[] arr1 = new int[N], arr2 = new int[N];
		for (char ch : word1.toCharArray())
			arr1[ch - 'a']++;
		for (char ch : word2.toCharArray())
			arr2[ch - 'a']++;

		/**
		 * checking Condition 1 1. Both strings should have common Alphabets . but it's
		 * count may differ
		 ***/
		// if one has a letter which another one doesn't have, dont exist
		for (int i = 0; i < N; i++) {
			if (arr1[i] == arr2[i]) {
				//both string has the same chat count.
				//both has non-zero value or ZERO value
				continue;
			}
			//If both string's char count does not match, both string should have the char.
			//Char count should not be zero
			if (arr1[i] == 0 || arr2[i] == 0) {
				return false;
			}
		}
		/***
		 * 2. The pattern of number of times a character appears should match
		 */
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		for (int i = 0; i < N; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

}
