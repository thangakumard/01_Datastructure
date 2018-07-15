package arrayAlgorithms;
import org.testng.annotations.Test;

public class Substring_02_InOrder {

	@Test
	public void Test(){
		printSubstrings("abcd");
	}
	
	private void printSubstrings(String input){
		for(int i=0; i < input.length(); i++){
			for(int j=i+1; j <= input.length(); j++){
				System.out.println(input.substring(i,j));
			}
		}
	}
}
