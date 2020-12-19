package algorithms.string;

import org.junit.Assert;
import org.testng.annotations.Test;

public class String11_stringHasSameCharacters {
	
	@Test
	public void test() {
		 Assert.assertTrue(closeStrings("abc","cba"));
		 Assert.assertFalse(closeStrings("abcd","cba"));
	}

	public boolean closeStrings(String word1, String word2) {
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
