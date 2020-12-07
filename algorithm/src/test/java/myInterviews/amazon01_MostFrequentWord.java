package myInterviews;

import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;


/********
 * Interview Date: 29_Apr_2018
 * @author THANGAKUMAR
 * In a given sentence print the most frequent words other than the words to be ignored
 * Sample Input : 
 *  our garden is 1mile from my home. we planted rose in our garden. now our garden is full of rose.
 *  rose perfumes are made from rose oil.
 *  
 *  WordstoIgnore = [is,in,our,of,are,from,we]
 *  
 *  Expected Output : [garden,rose]
 *  
 *  https://leetcode.com/problems/most-common-word/
 */

public class amazon01_MostFrequentWord {
	
	@Test
	public void printMostFrequent() {
		
		String input = "our garden is 1mile from my home. we planted rose in our garden. now our garden is full of rose. rose perfumes are made from rose oil";
		String[] WordstoIgnore = {"is","in","our","of","are","from","we"};
		List<String> lstWordstoIgnore = Arrays.asList(WordstoIgnore);
		HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
		
		String[] words = input.split("\\s+");
		for (String word : words) {
			if(!lstWordstoIgnore.contains(word)) {
				if(wordMap.containsKey(word)) {
					wordMap.put(word, wordMap.get(word) + 1);
				}
				else {
					wordMap.put(word, 1);
				}
			}
		}
		
		Set<String> finalWords = wordMap.keySet();
		List<String> result = new ArrayList<String>();
		Collections.sort(result, (w1,w2) -> wordMap.get(w1).equals(wordMap.get(w2))? w1.compareTo(w2) : wordMap.get(w2) - wordMap.get(w1));

		
		for(String word : finalWords) {
			if(wordMap.get(word) > 1) {
				System.out.println(word);
			}
		}
	}
	
	 public String mostCommonWord(String paragraph, String[] banned) {

	        // 1). replace the punctuations with spaces,
	        // and put all letters in lower case
	        String normalizedStr = paragraph.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();

	        // 2). split the string into words
	        String[] words = normalizedStr.split("\\s+");

	        Set<String> bannedWords = new HashSet();
	        for (String word : banned)
	            bannedWords.add(word);

	        Map<String, Integer> wordCount = new HashMap();
	        // 3). count the appearance of each word, excluding the banned words
	        for (String word : words) {
	            if (!bannedWords.contains(word))
	                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
	        }

	        // 4). return the word with the highest frequency
	        return Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue()).getKey();
	    }

}
