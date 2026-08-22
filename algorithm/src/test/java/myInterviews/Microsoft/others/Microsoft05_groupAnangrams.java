package myInterviews.Microsoft.others;

import java.util.*;

public class Microsoft05_groupAnangrams {
    /**
     * Let n = number of strings, k = max length of a string.
     * Time Complexity: O(n * k)
     * Space Complexity: O(n * k)
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String s : strs) {
            char[] count = new char[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String key = new String(count); // canonical signature, e.g. \u0001\u0000...\u0001...
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(groups.values());
    }

    /**
     * Let n = number of strings, k = max length of a string.
     * Time Complexity: O(n * k log k) --
     * Space Complexity: O(n * k)
     */
    public List<List<String>> groupAnagrams_II(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        if(strs.length == 0) return result;

        HashMap<String, List<String>> mapAnagram = new HashMap<>();

        for(String input: strs){
            char[] charInput =  input.toCharArray();
            Arrays.sort(charInput); //O(k log k)
            String sorted = new String(charInput);
            if(mapAnagram.containsKey(sorted)){
                mapAnagram.get(sorted).add(input);
            }else{
                List<String> lstString = new ArrayList<>();
                lstString.add(input);
                mapAnagram.put(sorted, lstString);
            }
        }
        result.addAll(mapAnagram.values());
        return result;
    }
}
