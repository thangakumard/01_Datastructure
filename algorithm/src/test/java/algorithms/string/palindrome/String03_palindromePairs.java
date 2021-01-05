

package algorithms.string.palindrome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

public class String03_palindromePairs {
	
	/*
	 * https://leetcode.com/problems/palindrome-pairs/
	 * 
		Given a list of unique words, return all the pairs of the distinct indices (i, j) in the given list, so that the concatenation of the two words words[i] + words[j] is a palindrome.
		
		 
		
		Example 1:
		
		Input: words = ["abcd","dcba","lls","s","sssll"]
		Output: [[0,1],[1,0],[3,2],[2,4]]
		Explanation: The palindromes are ["dcbaabcd","abcddcba","slls","llssssll"]
		Example 2:
		
		Input: words = ["bat","tab","cat"]
		Output: [[0,1],[1,0]]
		Explanation: The palindromes are ["battab","tabbat"]
		Example 3:
		
		Input: words = ["a",""]
		Output: [[0,1],[1,0]]
	 */
	@Test
	public void isPalindrome(){
		String[] words = {"abcd","dcba","lls","s","sssll"};
		System.out.println(palindromePairs(words)); //Expected: [[0, 1], [1, 0], [3, 2], [2, 4]]
	}

	private List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(words == null || words.length <= 1){
            return result;
        }
        for(int i=0; i < words.length-1; i++){
            for(int j = i + 1; j < words.length; j++){
                if(isPalindrome(words[i] + words[j])){
                    result.add(new ArrayList(Arrays.asList(i,j)));
                }

                  if(isPalindrome(words[j] + words[i])){
                    result.add(new ArrayList(Arrays.asList(j,i)));
                }
            }
        }
        return result;
    }
    
    private boolean isPalindrome(String str){
    int length = str.length();
    for(int i=0,j = length-1; i<j; i++,j--)
        if(str.charAt(i)!=str.charAt(j))
            return false;
    return true;
}
    
}
