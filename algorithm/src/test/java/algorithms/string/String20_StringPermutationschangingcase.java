package algorithms.string;
import java.util.*;

import org.testng.annotations.Test;

public class String20_StringPermutationschangingcase {

	  @Test
	  public void test() {
	    List<String> result = this.findLetterCaseStringPermutations("ad52");
	    System.out.println(" String permutations are: " + result);

	    result = this.findLetterCaseStringPermutations("ab7c");
	    System.out.println(" String permutations are: " + result);
	  }
	
	  public List<String> findLetterCaseStringPermutations(String str) {
	    List<String> permutations = new ArrayList<>();
	    List<StringBuilder> ans = new ArrayList<>();
	    ans.add(new StringBuilder(""));
	    for(Character c: str.toCharArray()){
	      int n = ans.size();
	       if(Character.isLetter(c)){
	         for(int i=0; i<n; i++){
	          ans.add(new StringBuilder(ans.get(i)));
	          ans.get(i).append(Character.toLowerCase(c));
	          ans.get(n-i).append(Character.toUpperCase(c));
	         }

	       }else{
	         for(int i=0; i<n; i++){
	           ans.get(i).append(c);
	         }
	       }
	    }
	    for(StringBuilder s: ans){
	      permutations.add(s.toString());
	    }


	    // TODO: Write your code here
	    return permutations;
	  }
}
