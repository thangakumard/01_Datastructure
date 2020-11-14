package myInterviews;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

}
