package algorithms.string.palindrome;
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
public class String11_PalindromePermutation_II {

	@Test
	private void test() {
		System.out.println(generatePalindromes("aabb"));
	}
	public List<String> generatePalindromes(String s) {
		LinkedList<String> result = new LinkedList<>();
        LinkedList<String> odd = new LinkedList<>();
        int[] char_count = new int[256];
        for(char c: s.toCharArray()) {
        	char_count[c]++;
        }
        for(int i=0;i<char_count.length;i++) {
        	if(char_count[i] % 2 != 0) {
        	 odd.add((char)i + "");
        	}
        }
        if(odd.size() > 1) return result;
        
        generate(result,char_count, odd.size()!=0? odd.getFirst():"",s.length());
        return result;
    }
	
	private void generate(List<String> r,int[] counts,String Curr,int len) {
		if(Curr.length() == len)
			r.add(Curr);
		else for(int i=0;i< counts.length;i++) {
			if(counts[i] > 1) {
				counts[i] -=2;
				generate(r,counts,((char)i) + Curr + ((char)i),len);
				counts[i] +=2;
			}
		}
	}
}
