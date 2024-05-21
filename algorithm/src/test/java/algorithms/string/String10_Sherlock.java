package algorithms.string;

import java.util.*;


import org.junit.Assert;
import org.testng.annotations.Test;

public class String10_Sherlock {
	
  /* https://www.hackerrank.com/challenges/sherlock-and-valid-string/
* Sherlock considers a string to be valid if all characters of the string appear the same number of times.
* It is also valid if he can remove just  character at  index in the string, and the remaining characters will occur the same number of times.
* Given a string , determine if it is valid. If so, return YES, otherwise return NO.

For example, if , it is a valid string because frequencies are . 
So is  because we can remove one  and have  of each character in the remaining string. 
If  however, the string is not valid as we can only remove  occurrence of . 
That would leave character frequencies of .

Function Description

Complete the isValid function in the editor below. It should return either the string YES or the string NO.

isValid has the following parameter(s):

s: a string
Input Format

A single string .

Constraints

Each character 
   */
	
	@Test
	public void IsSherlock(){
		Assert.assertEquals("NO", isValid("aabbcd"));
		Assert.assertEquals("YES", isValid("aabbc"));
		Assert.assertEquals("YES", isValid("abcdefghhgfedecba"));
		Assert.assertEquals("YES", isValid("aaabbbccccdddeee"));
		Assert.assertEquals("NO", isValid("aaabbbccdddeee"));
		Assert.assertEquals("YES", isValid("aaaabbbcccddd"));
		Assert.assertEquals("YES", isValid("abbbcccddd"));
		Assert.assertEquals("YES", isValid("abcdefghhgfedecba"));
		Assert.assertEquals("NO", isValid("aaaabbcc"));
		Assert.assertEquals("NO", isValid("xxxaabbccrry"));
	}
	
	// Complete the isValid function below.
    static String isValid(String s) {
    	if(s.length() == 1) return "YES";
    	
        int[] frequency = new int[26];
        
        for(int i=0; i < s.length(); i++){
            frequency[s.charAt(i) - 'a']++;
        }
        HashMap<Integer, List<Character>> mapCount = new HashMap<Integer,List<Character>>();

        for(int i=0; i< 26; i++)
        {
        	if(frequency[i] > 0) {
        		List<Character> charList = mapCount.getOrDefault(frequency[i], new ArrayList<>());
        		charList.add((char)(i+'a'));
        		mapCount.put(frequency[i], charList);
 
        	}
        	 
        }
        if(mapCount.size() == 1) {
        	return "YES";
        }
        if(mapCount.size() > 2) {
        	return "NO";
        }else if(mapCount.size() == 2) {
        	
        	Integer[] req_arr = new Integer[mapCount.size()];
        	Set<Integer> keys = mapCount.keySet();
        	req_arr = keys.toArray(req_arr);
        	
        	int min_frequ = Math.min(req_arr[0],req_arr[1]);
        	int max_frequ = Math.max(req_arr[0],req_arr[1]);
        	
        	List<Character> min_chars = mapCount.get(min_frequ);
        	List<Character> max_chars = mapCount.get(max_frequ);
        	
        	if(min_chars.size() == 1 || max_chars.size() == 1) {
        		if(min_frequ == 1 && min_chars.size() == 1)
        			return "YES";
        		if(min_frequ == 1 && min_chars.size() > 1)
        			return "NO";
        		if(max_frequ  > 1 && min_chars.size() > 1) {
        			if(max_chars.size() == 1) {
        				if(max_frequ - 1 == min_frequ) {
        					return "YES";
        				}
        			}else {
        				return "NO";
        			}
        		}
        		else {
        			return "NO";
        		}
        		
        		
        	}
        	else
        		return "NO";
        	
        	
        }
        
        return "NO"; 
    }

}
