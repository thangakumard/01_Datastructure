package algorithms.array;
import org.testng.annotations.Test;
/**********
 * 
 * https://www.careercup.com/question?id=12435684
 *
 **********/
public class Substring_01_PossibleCombination {

	@Test
	public void Test(){
		ListSubstringCombinations("abcd");
		//ListSubstringCombinations("aaa");
	}
	
	private void ListSubstringCombinations(String input){
		if(input != null && input.length() > 0)
		{
			buildSubstrings("",input);
		}
	}
	
	private void buildSubstrings(String prefix, String rest){
		if(rest.length() == 0){
			System.out.println(prefix + " ");
		}
		else{
			buildSubstrings(prefix + rest.charAt(0), rest.substring(1));
			buildSubstrings(prefix, rest.substring(1));
		}
	}
}
