package myInterviews;

import org.testng.annotations.Test;

/********
 * 
 * @author THANGAKUMAR
 * print all the possible combination of substring in a string
 * Input : ABC
 * OUTPUT :
 * 
 * A,AB,AC,B,BC,C,ABC
 * 
 */
public class amazon03_printPossibleCombinations {

	@Test
	public void possibleCombination(){
		String input = "ABCD";
		printCombination(input);
	}
	
	private void printCombination(String input){
		if(input == null || input.length() == 0)
			return;
		helper("", input);
	}
	
	private void helper(String prefix, String rest){
		if(rest.length() == 0){
			System.out.print(prefix + " ");
		}else{
			helper(prefix + rest.charAt(0), rest.substring(1));
			helper(prefix, rest.substring(1));
		}		
	}
}
