package algorithms.hashMap;

import java.util.*;
import org.testng.annotations.Test;

/*****
 * https://leetcode.com/problems/top-k-frequent-words/

Given a non-empty list of words, return the k most frequent elements.

Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word with the lower alphabetical order comes first.

Example 1:
Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
Output: ["i", "love"]
Explanation: "i" and "love" are the two most frequent words.
    Note that "i" comes before "love" due to a lower alphabetical order.
Example 2:
Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
Output: ["the", "is", "sunny", "day"]
Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
    with the number of occurrence being 4, 3, 2 and 1 respectively.
Note:
You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Input words contain only lowercase letters.
Follow up:
Try to solve it in O(n log k) time and O(n) extra space.
 *
 */
public class Map04_TopKFrequentWords {

	@Test
	  public void gettopKFrequent() {
		  String[] input = {"i", "love", "leetcode", "i", "love", "coding"};
		  List<String> result = topKFrequent(input , 2);
		  for(String word : result) {
			  System.out.println(word);
		  }
	  }
	
	public List<String> topKFrequent(String[] words, int k) {
        List<String> result = new ArrayList<>();
        
        if(words == null || words.length == 0)return result;
        
        HashMap<String, Integer> mapInput = new HashMap<String,Integer>();
        for(String s: words){
            mapInput.put(s, mapInput.getOrDefault(s,0)+1);
        }
        result.addAll(mapInput.keySet());
        Collections.sort(result, (a,b) -> mapInput.get(b).equals(mapInput.get(a)) ?
                         a.compareTo(b) : mapInput.get(b) - mapInput.get(a));
        
        return result.subList(0,k);
    }
}
