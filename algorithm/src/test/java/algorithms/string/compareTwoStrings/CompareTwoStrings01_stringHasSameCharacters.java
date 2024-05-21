package algorithms.string.compareTwoStrings;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * https://www.geeksforgeeks.org/quick-way-check-characters-string/
 *
 */
public class CompareTwoStrings01_stringHasSameCharacters {
	
	@Test
	public void test() {
        Assertions.assertThat(hasSameCharacters("abc","cba")).isTrue();
        Assertions.assertThat(hasSameCharacters("abcd","cba")).isFalse();
    }

	public boolean hasSameCharacters(String word1, String word2) {
        if(word1.length() != word2.length()){
            return false;
        }
        int x = 0 ;
        for(int i=0; i< word1.length(); i++){
            x = x ^ word1.charAt(i);
        }
        for(int i=0; i< word2.length(); i++){
            x = x ^ word2.charAt(i);
        }
        return (x == 0);
    }
}
