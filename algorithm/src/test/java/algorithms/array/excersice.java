package algorithms.array;

import java.util.*;

import org.testng.annotations.Test;

public class excersice {
	
	@Test
	public void test() {
		
		System.out.println("Is in pattern:" + wordPattern("aaa","cat cat cat"));
	}

	
	public boolean wordPattern(String pattern, String s) {
        
        String[] words = s.split("\\s");
        int l = pattern.toCharArray().length;
        
        if(words.length != l)
            return false;
        
        HashMap<Character, String> myMap = new HashMap<Character, String>();
        String word = "";
        for(int i=0; i < l; i++){
            if(myMap.containsKey(pattern.charAt(i))){
                word = myMap.get(pattern.charAt(i));
                if(!words[i].equals(word.trim()))
                    return false;
            }else{
                myMap.put(pattern.charAt(i),words[i]);
            }
            
        }
        
        return true;
        
    }
}
