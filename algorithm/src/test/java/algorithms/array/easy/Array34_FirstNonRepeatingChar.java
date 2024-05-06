package algorithms.array.easy;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/first-unique-character-in-a-string/
 */
public class Array34_FirstNonRepeatingChar {
	
	@Test
	private void test() {
		String input = "Thangakumar";
		System.out.println(firstUniqChar_array(input));
	}

	public int firstUniqChar_array(String s) {

     int[] counter = new int[26];
     for(char c: s.toCharArray()){
        counter[c-'a'] ++;
     }

     for(int i=0; i < s.length(); i++){
        if(counter[s.charAt(i) - 'a'] == 1) return i;
     }
        return -1;
    }
	
	 public int firstUniqChar(String s) {
     HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	int length = s.toCharArray().length;
	for(int i=0; i< length; i++){
		map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
	}
	for(int i= 0; i< length; i++){
		if(map.get(s.charAt(i)) == 1){
			return i;
		}
	}

	return -1;
    }

	

}
