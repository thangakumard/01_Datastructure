package algorithms.array;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.*;
/***********
 https://www.geeksforgeeks.org/z-algorithm-linear-time-pattern-searching-algorithm/
	What is Z Array?
	For a string str[0..n-1], Z array is of same length as string. An element Z[i] of Z array stores length of the longest substring starting from str[i] which is also a prefix of str[0..n-1]. The first entry of Z array is meaning less as complete string is always prefix of itself.
	
	Example:
	Index            0   1   2   3   4   5   6   7   8   9  10  11 
	Text             a   a   b   c   a   a   b   x   a   a   a   z
	Z values         X   1   0   0   3   1   0   0   2   2   1   0 
	More Examples:
	str  = "aaaaaa"
	Z[]  = {x, 5, 4, 3, 2, 1}
	
	str = "aabaacd"
	Z[] = {x, 1, 0, 2, 1, 0, 0}
	
	str = "abababab"
	Z[] = {x, 0, 6, 0, 4, 0, 2, 0}
 *
 */
public class Algor04_ZAlgorithm {
	
	@Test
	public void Test(){
		 String text = "aaabcxyzaaaabczaaczabbaaaaaabc";
	        String pattern = "aaabc";	        
	        List<Integer> result = matchPattern(text.toCharArray(), pattern.toCharArray());
	        result.forEach(System.out::println);
	}
	
	private int[] calculateZ(char input[]) {
        int Z[] = new int[input.length];
        int left = 0;
        int right = 0;
        for(int k = 1; k < input.length; k++) {
            if(k > right) {
                left = right = k;
                while(right < input.length && input[right] == input[right - left]) {
                    right++;
                }
                Z[k] = right - left;
                right--;
            } else {
                //we are operating inside box
                int k1 = k - left;
                //if value does not stretches till right bound then just copy it.
                if(Z[k1] < right - k + 1) {
                    Z[k] = Z[k1];
                } else { //otherwise try to see if there are more matches.
                    left = k;
                    while(right < input.length && input[right] == input[right - left]) {
                        right++;
                    }
                    Z[k] = right - left;
                    right--;
                }
            }
        }
        return Z;
    }

    /**
     * Returns list of all indices where pattern is found in text.
     */
    public List<Integer> matchPattern(char text[], char pattern[]) {
        char newString[] = new char[text.length + pattern.length + 1];
        int i = 0;
        for(char ch : pattern) {
            newString[i] = ch;
            i++;
        }
        newString[i] = '$';
        i++;
        for(char ch : text) {
            newString[i] = ch;
            i++;
        }
        List<Integer> result = new ArrayList<>();
        int Z[] = calculateZ(newString);

        for(i = 0; i < Z.length ; i++) {
            if(Z[i] == pattern.length) {
                result.add(i - pattern.length - 1);
            }
        }
        return result;
    }


}
