package algorithms.string;

import org.testng.Assert;
import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/expressive-words/
 *
 * Sometimes people repeat letters to represent extra feeling. For example:
 *
 * "hello" -> "heeellooo"
 * "hi" -> "hiiii"
 * In these strings like "heeellooo", we have groups of adjacent letters that are all the same: "h", "eee", "ll", "ooo".
 *
 * You are given a string s and an array of query strings words. A query word is stretchy if it can be made to be equal to s by any number of applications of the following extension operation: choose a group consisting of characters c, and add some number of characters c to the group so that the size of the group is three or more.
 * For example, starting with "hello", we could do an extension on the group "o" to get "hellooo", but we cannot get "helloo" since the group "oo" has a size less than three. Also, we could do another extension like "ll" -> "lllll" to get "helllllooo". If s = "helllllooo", then the query word "hello" would be stretchy because of these two extension operations: query = "hello" -> "hellooo" -> "helllllooo" = s.
 * Return the number of query strings that are stretchy.
 *
 * Example 1:
 * Input: s = "heeellooo", words = ["hello", "hi", "helo"]
 * Output: 1
 * Explanation:
 * We can extend "e" and "o" in the word "hello" to get "heeellooo".
 * We can't extend "helo" to get "heeellooo" because the group "ll" is not size 3 or more.

 * Example 2:
 * Input: s = "zzzzzyyyyy", words = ["zzyy","zy","zyy"]
 * Output: 3
 *
 * Constraints:
 * 1 <= s.length, words.length <= 100
 * 1 <= words[i].length <= 100
 * s and words[i] consist of lowercase letters.
 */
public class String41_ExpressiveWords {

	@Test
	private void test() {
		String S= "heeellooo";
		String[] words = new String[] {"hello", "hi", "helo"};
		Assert.assertEquals(expressiveWords(S,words), 1);
	}
	public int expressiveWords(String S, String[] words) {
        int count = 0;
        for(String input: words){
            if(isStretchy(S, input))
                count++;
        }
        return count;
    }
    
    private boolean isStretchy(String s, String t){
    
        if(t == null) return false;
        int l1 = 0, l2 = 0;
        
        while(l1 < s.length() && l2 < t.length()){
            if(s.charAt(l1) != t.charAt(l2)) return false;
            
            int c1 =  getCharCount(s, l1);
            int c2 = getCharCount(t, l2);
            
            if(c2 > c1) return false;
            if(c1 < 3 && c1 != c2) return false;
            l1 = l1 + c1; 
            l2 = l2 + c2;
        }
        return (l1 == s.length() && l2 == t.length());
    }
    
    private int getCharCount(String s, int i){
        int j = i;
        while(j < s.length()){
            if(s.charAt(j) == s.charAt(i)){
                j++;
            }else{
                break;
            }
        }
        return j-i;
    }
}
