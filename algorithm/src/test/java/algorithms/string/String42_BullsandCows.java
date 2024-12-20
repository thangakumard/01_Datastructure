package algorithms.string;

import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.testng.annotations.*;

/****
 * https://leetcode.com/problems/bulls-and-cows/
 *
 * You are playing the Bulls and Cows game with your friend.
  You write down a secret number and ask your friend to guess what the number is.
 When your friend makes a guess, you provide a hint with the following info:
The number of "bulls", which are digits in the guess that are in the correct position.
The number of "cows", which are digits in the guess that are in your secret number but are located in the wrong position. Specifically, the non-bull digits in the guess that could be rearranged such that they become bulls.
Given the secret number secret and your friend's guess guess, return the hint for your friend's guess.

The hint should be formatted as "xAyB", where x is the number of bulls and y is the number of cows. Note that both secret and guess may contain duplicate digits.

Example 1:
Input: secret = "1807", guess = "7810"
Output: "1A3B"
Explanation: Bulls are connected with a '|' and cows are underlined:
"1807"
  |
"7810"

Example 2:
Input: secret = "1123", guess = "0111"
Output: "1A1B"
Explanation: Bulls are connected with a '|' and cows are underlined:
"1123"        "1123"
  |      or     |
"0111"        "0111"
Note that only one of the two unmatched 1s is counted as a cow since the non-bull digits can only be rearranged to allow one 1 to be a bull.

 Example 3:
Input: secret = "1", guess = "0"
Output: "0A0B"

 Example 4:
Input: secret = "1", guess = "1"
Output: "1A0B"

Constraints:
1 <= secret.length, guess.length <= 1000
secret.length == guess.length
secret and guess consist of digits only.
 * @author thangakumar
 *
 */
public class String42_BullsandCows {

	@Test
	private void test() {
		Assertions.assertThat(getHint_2_loop("1807", "7810")).isEqualTo("1A3B");
        Assertions.assertThat(getHint_2_loop("1122", "1222")).isEqualTo("3A0B");
	}
	
	public String getHint_2_loop(String secret, String guess) {
        if(secret.length() != guess.length()){
            return "0A0B";
        }
        HashMap<Character, Integer> charMap = new HashMap<Character,Integer>();
        for(char c: secret.toCharArray()){
            charMap.put(c,charMap.getOrDefault(c,0)+1);
        }
        int bull = 0;
        int cow = 0;
        
        for(int i=0; i < secret.length(); i++){
            if(charMap.containsKey(guess.charAt(i))){
                if(guess.charAt(i) == secret.charAt(i)){
                    bull++;
                    charMap.put(guess.charAt(i), charMap.get(guess.charAt(i))-1);
                    if(charMap.get(guess.charAt(i)) == 0){
                        charMap.remove(guess.charAt(i));
                    }
                }
            }
        }
        for(int i=0; i < secret.length(); i++){
            if(charMap.containsKey(guess.charAt(i))){
                if(guess.charAt(i) != secret.charAt(i)){
                    cow++;
                    charMap.put(guess.charAt(i), charMap.get(guess.charAt(i))-1);
                    if(charMap.get(guess.charAt(i)) == 0){
                        charMap.remove(guess.charAt(i));
                    }
                }
            }
        }
        
        return bull + "A" + cow + "B";

    }
	public String getHint(String secret, String guess) {
	        HashMap<Character, Integer> h = new HashMap();
	            
	        int bulls = 0, cows = 0;
	        int n = guess.length();
	        for (int idx = 0; idx < n; ++idx) {
	            char s = secret.charAt(idx);
	            char g = guess.charAt(idx);
	            if (s == g) {
	                bulls++;    
	            } else {
	                if (h.getOrDefault(s, 0) < 0) 
	                    cows++;
	                if (h.getOrDefault(g, 0) > 0)
	                    cows++;
	                h.put(s, h.getOrDefault(s, 0) + 1);
	                h.put(g, h.getOrDefault(g, 0) - 1);
	            }
	        }      
	        return Integer.toString(bulls) + "A" + Integer.toString(cows) + "B";
	    }
}
