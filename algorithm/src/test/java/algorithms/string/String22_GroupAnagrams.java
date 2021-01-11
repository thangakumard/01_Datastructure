package algorithms.string;

import java.util.*;

import org.testng.annotations.Test;

public class String22_GroupAnagrams {
	
	
@Test
private void test() {
	 String[] strs =  {"eat","tea","tan","ate","nat","bat"};
	 System.out.println(groupAnagrams(strs));
	 
}
	
public List<List<String>> groupAnagrams(String[] strs) {
        
        List<List<String>> result = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        
        for(String word: strs){
            char[] current = word.toCharArray();
            Arrays.sort(current);
            String sorted = new String(current);
            List<String> anagrams = map.getOrDefault(sorted, new ArrayList<>());
            anagrams.add(word);
            map.put(sorted, anagrams);
        }
        result.addAll(map.values());
        return result;
    }
}
