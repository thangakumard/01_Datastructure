package algorithms.string;

import org.testng.Assert;
import org.testng.annotations.Test;

public class String41_ExpressiveWords {

	@Test
	private void test() {
		String S= "heeellooo";
		String[] words = new String[] {"hello", "hi", "helo"};
		Assert.assertEquals(expressiveWords(S,words), 1);
	}
	public int expressiveWords(String S, String[] words) {
        int count = 0;
        for(String input: words){
            if(isStretchy(S, input))
                count++;
        }
        return count;
    }
    
    private boolean isStretchy(String s, String t){
    
        if(t == null) return false;
        int l1 = 0, l2 = 0;
        
        while(l1 < s.length() && l2 < t.length()){
            if(s.charAt(l1) != t.charAt(l2)) return false;
            
            int c1 =  getCharCount(s, l1);
            int c2 = getCharCount(t, l2);
            
            if(c2 > c1) return false;
            if(c1 < 3 && c1 != c2) return false;
            l1 = l1 + c1; 
            l2 = l2 + c2;
        }
        return (l1 == s.length() && l2 == t.length());
    }
    
    private int getCharCount(String s, int i){
        int j = i;
        while(j < s.length()){
            if(s.charAt(j) == s.charAt(i)){
                j++;
            }else{
                break;
            }
        }
        return j-i;
    }
}
