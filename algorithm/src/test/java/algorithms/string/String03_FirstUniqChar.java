package algorithms.string;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.HashMap;


public class String03_FirstUniqChar {
	
	@Test
	public void test() {
		Assertions.assertThat(withSingleArray("leetcode")).isEqualTo(0);
		Assertions.assertThat(withSingleArray("loveleetcode")).isEqualTo(2);
		Assertions.assertThat(withSingleArray("aabb")).isEqualTo(-1);
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
