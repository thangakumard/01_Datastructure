package algorithms.array.medium.hashMap;

import java.util.*;

import org.testng.annotations.Test;

/*
 * You're given strings J representing the types of stones that are jewels, and S representing the stones you have.  Each character in S is a type of stone you have.  You want to know how many of the stones you have are also jewels.

The letters in J are guaranteed distinct, and all characters in J and S are letters. Letters are case sensitive, so "a" is considered a different type of stone from "A".

Example 1:

Input: J = "aA", S = "aAAbbbb"
Output: 3
Example 2:

Input: J = "z", S = "ZZ"
Output: 0
Note:

S and J will consist of letters and have length at most 50.
The characters in J are distinct.
 */

public class MAP02_JewelsAndStones {
	
	@Test
	private void test() {
		String J = "aA", S = "aAAbbbb";
		System.out.println(numJewelsInStones(J, S));
	}
	
	public int numJewelsInStones(String J, String S) {
        int result = 0;
        Set<Character> numSet = new HashSet<>();
        Map<Character, Integer> numMap = new HashMap<Character, Integer>();
        
        for(int i=0; i< J.length(); i++){
            numSet.add(J.charAt(i));
        }
        for(int i=0; i< S.length(); i++){
            numMap.put(S.charAt(i), numMap.getOrDefault(S.charAt(i), 0)+1);
        }
        for(Map.Entry<Character,Integer> map: numMap.entrySet()){
            if(numSet.contains(map.getKey())){
                result += map.getValue();
            }
        }
        return result;
    }

}
