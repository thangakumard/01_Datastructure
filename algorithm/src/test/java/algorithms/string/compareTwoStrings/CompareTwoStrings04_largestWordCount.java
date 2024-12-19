package algorithms.string.compareTwoStrings;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/sender-with-largest-word-count/
 */
public class CompareTwoStrings04_largestWordCount {
    public String largestWordCount(String[] messages, String[] senders) {
        HashMap<String,Integer> mapWordCounter=new HashMap<>();
        int maxWordCount = 0;
        String maxWordSender = "";

        for(int i=0;i<messages.length;i++){
            String[] words = messages[i].split(" ");
            int wordCount = mapWordCounter.getOrDefault(senders[i],0)+words.length;
            mapWordCounter.put(senders[i],wordCount);

            if(mapWordCounter.get(senders[i]) > maxWordCount){
                maxWordCount = mapWordCounter.get(senders[i]);
                maxWordSender = senders[i];
            }
            else if(mapWordCounter.get(senders[i]) == maxWordCount && maxWordSender.compareTo(senders[i])<0){
                maxWordSender = senders[i];
            }
        }
        return maxWordSender;
    }
}
