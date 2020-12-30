package algorithms.string.palindrome;

public class String04_Palindrome_Longest_permutation {
	
	/*
	 * https://leetcode.com/problems/longest-palindrome/
	 * 409. Longest Palindrome
		Easy
		
		1382
		
		95
		
		Add to List
		
		Share
		Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.
		
		Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
		
		 
		
		Example 1:
		
		Input: s = "abccccdd"
		Output: 7
		Explanation:
		One longest palindrome that can be built is "dccaccd", whose length is 7.
		Example 2:
		
		Input: s = "a"
		Output: 1
		Example 3:
		
		Input: s = "bb"
		Output: 2
		 
		
		Constraints:
		
		1 <= s.length <= 2000
		s consists of lowercase and/or uppercase English letters only.
	 */
	public int longestPalindrome(String s) {
        int[] char_counts = new int[128];
        char[] char_input = s.toCharArray();
        
        for(char c:char_input){
            char_counts[c]++;
        }
        int result = 0;
        for(Integer char_count:char_counts){
            result += char_count/2 * 2;
            if(result % 2 == 0 && char_count%2 == 1){
                result += 1;
            }
        }
        return result;
    }

}
