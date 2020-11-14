package algorithms.priorityQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import bsh.Console;

public class topKFrequentWords {
	
	/*
	 * Given a non-empty list of words, return the k most frequent elements.

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
		    
		https://leetcode.com/problems/top-k-frequent-words/
	 */
	
	  @Test
	  public void gettopKFrequent() {
		  String[] input = {"i", "love", "leetcode", "i", "love", "coding"};
		  List<String> result = topKFrequent_01(input , 2);
		  for(String word : result) {
			  System.out.println(word);
		  }
	  }
	  
	  public List<String> topKFrequent_01(String[] words, int k) {
	        List<String> result = new LinkedList<>();
	        Map<String, Integer> map = new HashMap<>();
	        for(int i=0; i<words.length; i++)
	        {
	            if(map.containsKey(words[i]))
	                map.put(words[i], map.get(words[i])+1);
	            else
	                map.put(words[i], 1);
	        }
	        
	        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
	                 (a,b) -> a.getValue()==b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue()-b.getValue()
	        );
	        
	        for(Map.Entry<String, Integer> entry: map.entrySet())
	        {
	            pq.offer(entry);
	            if(pq.size()>k)
	                pq.poll();
	        }

	        while(!pq.isEmpty())
	            result.add(0, pq.poll().getKey());
	        
	        return result;
	    }


	  public List<String> topKFrequent_02(String[] words, int k) {
		  List<String> result = new LinkedList<>();
		  
		  Map<String,Integer> wordsMap = new HashMap();
		  for(String word: words) {
			  wordsMap.put(word, wordsMap.getOrDefault(word, 0)+1);
		  }
		  
		  List<String> candidates = new ArrayList(wordsMap.keySet());
		  Collections.sort(candidates, (w1,w2) -> wordsMap.get(w1).equals(wordsMap.get(w2))? w1.compareTo(w2) : wordsMap.get(w2) - wordsMap.get(w1));
		  
		  return candidates.subList(0, k);
	  }
}
