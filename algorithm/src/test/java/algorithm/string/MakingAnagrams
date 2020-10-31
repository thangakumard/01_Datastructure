package algorithm.string;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;


public class MakingAnagrams {
	
	@Test
	public void makeAnagrams(){
		Assert.assertEquals(6, Solution1("abcdef","abcd"));
		Assert.assertEquals(6, Solution2("abcdef","abcd"));
	}

	// This solution will work when input string has ONLY Alphabets
	static int Solution1(String a, String b) { 
		int min_delete =0;
		int[] a_frequency = new int[26];
		int[] b_frequency = new int[26];
		
		for(int i=0; i< a.length(); i++) {
			int char_value = (int) a.charAt(i);
			int position = char_value - (int)'a';
			a_frequency[position]++;
		}
		for(int i=0; i< b.length(); i++) {
			int char_value = (int) b.charAt(i);
			int position = char_value - (int)'a';
			System.out.println("char_value = "+ char_value);
			System.out.println("(int)'a' = "+ (int)'a');
			System.out.println("position = "+ position);
			b_frequency[position]++;
		}
		for(int i=0; i < 26; i++) {
			min_delete += Math.abs(a_frequency[i] - b_frequency[i]);
		}
		return min_delete;
	}
	
	// Returns number of char to be removed to make 2 strings as Anagram
    static int Solution2(String a, String b) {
        HashMap<Character, Integer> aMap = new HashMap<>();
        HashMap<Character, Integer> bMap = new HashMap<>();
        for(int i =0; i < a.length(); i++){
            int v = 0;
            if(aMap.containsKey(a.charAt(i))){
                 v = aMap.get(a.charAt(i));
                aMap.put(a.charAt(i), v+1);
            }else{
                aMap.put(a.charAt(i), v+1);
            }
        }
        for(int i =0; i < b.length(); i++){
            int v = 0;
            if(bMap.containsKey(b.charAt(i))){
                v = bMap.get(b.charAt(i));
                bMap.put(b.charAt(i), v+1);
            }else{
                bMap.put(b.charAt(i), v+1);
            }
        }
        int aRemove = 0;
    for (Map.Entry<Character, Integer> entry : aMap.entrySet()) {
        Character k = entry.getKey();
        Integer v = entry.getValue();
        if(bMap.containsKey(entry.getKey())){
            int x = bMap.get(k);
            if(v > x){
                aRemove += v-x;
            }
        }else{
            aRemove += v;
        }

    }
        int bRemove = 0;
       for (Map.Entry<Character, Integer> entry : bMap.entrySet()) {
           Character k = entry.getKey();
           Integer v = entry.getValue();
        if(aMap.containsKey(k)){
            int x = aMap.get(k);
            if(v > x){
                bRemove += v-x;
            }
        }else{
            bRemove += v;
        }
    }
        return aRemove+bRemove;
    }
}
