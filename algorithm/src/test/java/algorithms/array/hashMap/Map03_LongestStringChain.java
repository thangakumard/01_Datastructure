package algorithms.array.medium.hashMap;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

/*****
 * https://leetcode.com/problems/longest-string-chain/
 * 
Given a list of words, each word consists of English lowercase letters.

Let's say word1 is a predecessor of word2 if and only if we can add exactly one letter anywhere in word1 to make it equal to word2.
 For example, "abc" is a predecessor of "abac".

A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1, where word_1 is a predecessor of word_2, word_2 is a predecessor of word_3, and so on.

Return the longest possible length of a word chain with words chosen from the given list of words.

 

Example 1:

Input: words = ["a","b","ba","bca","bda","bdca"]
Output: 4

Explanation: One of the longest word chain is "a","ba","bda","bdca".
Example 2:

Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
Output: 5

Constraints:

1 <= words.length <= 1000
1 <= words[i].length <= 16
words[i] only consists of English lowercase letters.
 *
 */

public class Map03_LongestStringChain {

	@Test
	private void test() {
		String[] input = new String[] {"a","b","ba","bca","bda","bdca"};
		System.out.println(longestStrChain(input));
	}
	
	public int longestStrChain(String[] words) {
        
		if(words == null || words.length == 0) return 0;
		int res = 0;
		Arrays.sort(words, (a,b)-> a.length()-b.length());  // Sort the words based on their lengths
		HashMap<String, Integer> map = new HashMap();       //Stores each word and length of its max chain.

		for(String w : words) {                             //From shortest word to longest word
			map.put(w, 1);                                  //Each word is atleast 1 chain long
			for(int i=0; i<w.length(); i++) {               //Form next word removing 1 char each time for each w
				StringBuilder sb = new StringBuilder(w);
				String next = sb.deleteCharAt(i).toString();
				if(map.containsKey(next) && map.get(next)+1 > map.get(w))
					map.put(w, map.get(next)+1);            //If the new chain is longer, update the map
			}
			res = Math.max(res, map.get(w));                //Store max length of all chains
		}
		return res;
	}
}
