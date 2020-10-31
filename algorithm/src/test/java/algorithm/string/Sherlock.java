package algorithm.string;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.testng.annotations.Test;

public class Sherlock {
	
  //https://www.hackerrank.com/challenges/sherlock-and-valid-string/
	@Test
	public void IsSherlock(){
		Assert.assertEquals("NO", isValid("aabbcd"));
		Assert.assertEquals("YES", isValid("aabbc"));
		Assert.assertEquals("YES", isValid("abcdefghhgfedecba"));
		Assert.assertEquals("YES", isValid("aaabbbccccdddeee"));
		Assert.assertEquals("NO", isValid("aaabbbccdddeee"));
		Assert.assertEquals("YES", isValid("aaaabbbcccddd"));
		Assert.assertEquals("NO", isValid("abbbcccddd"));
	}
	
	// Complete the isValid function below.
    static String isValid(String s) {
        int[] frequency = new int[26];
        
        for(int i=0; i < s.length(); i++){
            int char_in_value = (int) s.charAt(i);
            int position = char_in_value - (int)'a';
            frequency[position]++;
        }
        HashMap<Integer, Integer> mapCount = new HashMap<Integer,Integer>();

        for(int i=0; i< 26; i++)
        {
        	if(frequency[i] > 0) {
        	 
        		if(mapCount.containsKey(frequency[i])){
        			
        			mapCount.put(frequency[i], mapCount.get(frequency[i])+1);
        		}
        		else {
        			mapCount.put(frequency[i], 1);
        		}
        		
        	}
        	 
        }
        if(mapCount.size() > 2) {
        	return "NO";
        }else if(mapCount.size() == 2) {        	
        	
        	int firstValue = 0, secondValue = 0;
        	for(Map.Entry<Integer, Integer> entry: mapCount.entrySet()) {
        		if(firstValue == 0)
        		{
        			firstValue = entry.getKey();
        		}else {
        			secondValue = entry.getKey();
        		}
        	}
        	int diff = Math.abs(firstValue - secondValue);
        	if(diff > 1) {
        		return "NO";
        	}else {
        		if(mapCount.get(firstValue) == 1 || mapCount.get(secondValue) == 1) {
        			int minValue = Math.min(firstValue, secondValue);
        			if(mapCount.get(minValue) == 1) {
        				if(minValue != 1) {
        				return "NO";
        				}
        			}
        		}else {
        			return "NO";
        		}
        	}
        	
        }
        
        return "YES"; 
    }

}
