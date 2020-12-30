package algorithms.string;

import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;


public class String14_FirstUniqChar {
	
	@Test
	public void test() {
		Assert.assertEquals(-1,withSinleIteration("aadadaad"));
		Assert.assertEquals(0,withSinleIteration("leetcode"));
		Assert.assertEquals(0,withSingleArray("leetcode"));
		Assert.assertEquals(0,WithHashMap("leetcode"));
	}

	
/*
 * Time complexity O(n) => with single for loop
 * Space complexity O(n)
 */
	private int withSinleIteration(String s) {
	   
        HashMap<Character,Integer> mapChar = new LinkedHashMap<Character,Integer>();
        Set<Character> charSet = new HashSet<>();
        
        for(int i=0; i < s.length(); i++){
            if(mapChar.containsKey(s.charAt(i))){
                mapChar.remove(s.charAt(i));
            }else{
            	if(!charSet.contains(s.charAt(i)))
                mapChar.put(s.charAt(i), i);
            }
            charSet.add(s.charAt(i));
        }
        
       return (mapChar.size() == 0) ? -1 : mapChar.entrySet().iterator().next().getValue();
    }


 	/*
 	 * With the assumption of input string will have only lower case alphabets
 	 * Time complexity O(n)
 	 * Space complexity O(n)
 	 */
	private int withSingleArray(String s) {
		
		int[] freq = new int[26];
		
		for(int i=0; i< s.length(); i++) {
			freq[s.charAt(i) - 'a'] ++;
		}
		
		for(int i=0; i < s.length(); i++) {
			if(freq[s.charAt(i) - 'a'] == 1) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	/*
	 * Time complexity O(n)
	 * Space complexity O(n)
	 */
	private int WithHashMap(String s) {
		HashMap<Character, Integer> map = new HashMap<Character,Integer>();
		
		for(int i=0; i < s.length(); i++) {
			map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
		}
		
		for(int i=0; i < s.length(); i++) {
			if(map.get(s.charAt(i)) == 1)
				return i;
		}
		
		return -1;
	}
}
