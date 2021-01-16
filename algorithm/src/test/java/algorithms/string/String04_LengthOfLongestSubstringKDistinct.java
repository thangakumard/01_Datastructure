package algorithms.string;

import java.util.Collections;
import java.util.HashMap;

import org.testng.annotations.Test;

public class String04_LengthOfLongestSubstringKDistinct {
	
	@Test
	public void test() {
	    System.out.println("Length of the longest substring: " + this.lengthOfLongestSubstringKDistinct("araaci", 2));
	    System.out.println("Length of the longest substring: " + this.lengthOfLongestSubstringKDistinct("araaci", 1));
	    System.out.println("Length of the longest substring: " + this.lengthOfLongestSubstringKDistinct("cbbebi", 3));
	  }
	
	public int lengthOfLongestSubstringKDistinct(String s, int k) {
        char[] input = s.toCharArray();
        
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max_length = 0, i =0, j = 0;
        
        while(j < input.length){
            if(map.size() <= k){
                map.put(input[j], j++);                
            }
            if(map.size() > k){
                int index_to_delete = Collections.min(map.values());
                map.remove(input[index_to_delete]);
                i = index_to_delete + 1;
            }
            max_length = Math.max(max_length, j-i);
        }
        return max_length;
    }

}
