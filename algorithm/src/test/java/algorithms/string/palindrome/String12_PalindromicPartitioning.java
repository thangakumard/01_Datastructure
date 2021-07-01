package algorithms.string.palindrome;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/JP9wy4p5682
 * https://leetcode.com/problems/palindrome-partitioning/
 * 
 * Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.

	A palindrome string is a string that reads the same backward as forward.
	
	 
	
	Example 1:
	
	Input: s = "aab"
	Output: [["a","a","b"],["aa","b"]]
	Example 2:
	
	Input: s = "a"
	Output: [["a"]]
	 
	
	Constraints:
	
	1 <= s.length <= 16
	s contains only lowercase English letters.

 */
public class String12_PalindromicPartitioning {
	
	@Test
	private void test() {
		List<List<String>> result = partition("aab");
		for(List<String> lst: result) {
			System.out.println(lst.toString());
		}
	}

	public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtracking(result, s, new ArrayList<>(), 0);
        return result;
            
    }
    private void backtracking(List<List<String>> result, String s, List<String> lsttemp, int index){
        if(index == s.length()){
            result.add(new ArrayList<>(lsttemp));
            return;
        }
        
        for(int i=index; i< s.length(); i++){
            if(isPalindrome(s, index, i)){
                lsttemp.add(s.substring(index, i+1));
                System.out.println("Adding :" + s.substring(index, i+1));
                backtracking(result,s,lsttemp,i+1);
                System.out.println("Removing :" + lsttemp.get(lsttemp.size()-1));
                lsttemp.remove(lsttemp.size()-1);
            }
        }
    }
    
    private boolean isPalindrome(String s, int left, int right){
        while(left < right){
            if(s.charAt(left) != s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
