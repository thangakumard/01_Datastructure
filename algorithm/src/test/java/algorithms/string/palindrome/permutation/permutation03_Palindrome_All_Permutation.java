package algorithms.string.palindrome.permutation;

import java.util.*;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/palindrome-permutation-ii/
 * 
 * Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.

	Example 1:
	
	Input: "aabb"
	Output: ["abba", "baab"]
	Example 2:
	
	Input: "abc"
	Output: []
 */
public class permutation03_Palindrome_All_Permutation {

	@Test
	private void test() {
		System.out.println(generatePalindromes("aabb"));
	}

	public List<String> generatePalindromes(String s) {
		List<String> result = new ArrayList<>();
		if(s == null || s.length() == 0) return result;

		int[] freq = new int[26];
		List<Character> oddChar = new ArrayList<>();

		for(char c: s.toCharArray()){
			freq[c - 'a']++;
		}
		for(int i=0; i < 26;i++){
			if(freq[i] % 2!= 0){
				oddChar.add((char) (i +'a'));
			}
		}
		if(oddChar.size() > 1){
			return result;
		}

		backTracking(result, freq, s, oddChar.size() == 1 ? oddChar.get(0) + "": "");
		return result;
	}

	private void backTracking(List<String> result, int[] freq, String s, String temp){
		if(temp.length() == s.length()){
			result.add(temp);
			return;
		}

		for(int i = 0; i < freq.length; i++){
			if(freq[i] > 1){
				freq[i] -= 2;
				backTracking(result, freq, s, ((char) (i+'a') + temp + (char)(i+'a')));
				freq[i] += 2;
			}
		}
	}
}
