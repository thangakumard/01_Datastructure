package algorithms.string.compareTwoStrings;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/sender-with-largest-word-count/
 *
 * Time Complexity: O(n·m)
 * ================
 * n: number of messages
 * m: average length of each message (in characters)
 *
 * Space Complexity: O(s + m)
 *================
 * s: number of unique senders (HashMap size)
 * m: size of the words array from a single split operation (reused per iteration)
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
