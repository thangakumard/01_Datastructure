package algorithms.string;

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
                int min_index = input.length;
                for(int value: map.values()){
                    min_index = Math.min(min_index, value);
                }
                i = min_index+1;
                map.remove(input[min_index]);
            }
            max_length = Math.max(max_length, j-i);
        }
        return max_length;
    }

}
