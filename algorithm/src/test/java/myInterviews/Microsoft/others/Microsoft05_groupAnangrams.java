package myInterviews.Microsoft.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Microsoft05_groupAnangrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        if(strs.length == 0) return result;

        HashMap<String, List<String>> mapAnagram = new HashMap<>();

        for(String input: strs){
            char[] charInput =  input.toCharArray();
            Arrays.sort(charInput);
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
