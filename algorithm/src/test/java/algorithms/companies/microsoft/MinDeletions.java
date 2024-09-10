package algorithms.companies.microsoft;

import org.testng.annotations.Test;

import java.util.HashSet;

/**
 * https://leetcode.com/problems/minimum-deletions-to-make-character-frequencies-unique/
 */

public class MinDeletions {

    @Test
    public int minDeletions_test(String s) {

        int result = 0;
        int[] counter = new int[126];
        for(char c: s.toCharArray()){
            counter[c - 'a']++;
        }

        HashSet<Integer> set = new HashSet<Integer>();

        for(int i: counter){
            if(set.contains(i)){
                while(i>0 && set.contains(i)){
                    i--;
                    result++;
                }
            }
            if(i > 0)
                set.add(i);
        }

        return result;
    }
}
