package algorithms.string;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;

/***
 * https://leetcode.com/problems/word-pattern/
 Given a pattern and a string s, find if s follows the same pattern.
 Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in s.

 Example 1:
 Input: pattern = "abba", s = "dog cat cat dog"
 Output: true

 Example 2:
 Input: pattern = "abba", s = "dog cat cat fish"
 Output: false

 Example 3:
 Input: pattern = "aaaa", s = "dog cat cat dog"
 Output: false

 Constraints:

 1 <= pattern.length <= 300
 pattern contains only lower-case English letters.
 1 <= s.length <= 3000
 s contains only lowercase English letters and spaces ' '.
 s does not contain any leading or trailing spaces.
 All the words in s are separated by a single space.
 */
public class String18_WordPattern {

    @Test
    public void wordPatternTest(){
        Assertions.assertThat(twoHashMapSolution("abba", "dog cat cat dog")).isTrue();
        Assertions.assertThat(twoHashMapSolution("abba", "dog dog dog dog")).isFalse();

        Assertions.assertThat(singleHashMapSolution("abba", "dog cat cat dog")).isTrue();
        Assertions.assertThat(singleHashMapSolution("abba", "dog dog dog dog")).isFalse();
    }
    public boolean twoHashMapSolution(String pattern, String s) {
        String[] input = s.split(" ");

        if(input.length != pattern.length())
            return false;

        HashMap<Character, String> mapChar1 = new HashMap<>();
        HashMap<String, Character> mapChar2 = new HashMap<>();
        for(int i=0; i < pattern.length(); i++){
            if(!mapChar1.containsKey(pattern.charAt(i))){
                mapChar1.put(pattern.charAt(i), input[i]);
            }else{
                if(!mapChar1.get(pattern.charAt(i)).equals(input[i])){
                    return false;
                }
            }

            if(!mapChar2.containsKey(input[i])){
                mapChar2.put(input[i], pattern.charAt(i));
            }else{
                if(mapChar2.get(input[i]) != pattern.charAt(i)){
                    return false;
                }
            }

        }
        return true;
    }

    public boolean singleHashMapSolution(String pattern, String s){
            HashMap map_index = new HashMap();
            String[] words = s.split(" ");

            if (words.length != pattern.length())
                return false;

            for(Integer i=0; i < words.length; i++){
                char c = pattern.charAt(i);
                String w = words[i];

                if (!map_index.containsKey(c))
                    map_index.put(c, i);

                if (!map_index.containsKey(w))
                    map_index.put(w, i);

                if (map_index.get(c) != map_index.get(w))
                    return false;
            }
            return true;
    }
}
