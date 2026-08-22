package algorithms.array;

/***
 * https://www.geeksforgeeks.org/searching-for-patterns-set-2-kmp-algorithm/
 * Knuth�Morris�Pratt(KMP) Pattern Matching(Substring search)
 *
	Input:  txt[] = "THIS IS A TEST TEXT"
	        pat[] = "TEST"
	Output: Pattern found at index 10
	
	Input:  txt[] =  "AABAACAADAABAABA"
	        pat[] =  "AABA"
	Output: Pattern found at index 0
	        Pattern found at index 9
	        Pattern found at index 12
 */
import org.testng.annotations.*;

public class Algor03_KMP_PatternMatching {
	
	@Test
	public void Test(){		
		String Input = "abcxabcdabcdabcy";
		String pattern = "abcdabcy";
		
//		String Input = "AACAACAADAABAABA";
//		String pattern = "AABA";
		
		if(KMP(Input, pattern)){
			System.out.println("************ PATTERN MATCHES ***************");
		}else{
			System.out.println("??????????? PATTERN DOESN'T MATCH ???????");
		}		
	}
	
	public int[] fillTempPatternArray(String pattern){
	
		int[] temp = new int[pattern.length()];
		temp[0] = 0;
		int i = 1, j = 0;
		while(i < pattern.length() && j < pattern.length()-1){						
			if(pattern.charAt(i) == pattern.charAt(j)){ // If both are same char 				
				temp[i] = j+1; //Index of matching char +1
				i++;
				j++;
			}else{
				if(j == 0){
					temp[i] = 0;
					i++;
				}
				else{
					j = temp[j-1]; // If char doesn't match. start comparison from char at temp[j-1]
				}
			}
		}
		
		System.out.println("Patten Temp Array:");
		for(int k=0; k < pattern.length(); k++){
			System.out.print(temp[k] + ", ");
		}
		System.out.println();
		return temp;	
	}
	
	public boolean KMP(String text, String pattern){		
		
		int[] temp = fillTempPatternArray(pattern);
		int i=0;
		int j= 0;
		while(i < text.length() && j < pattern.length()){
			if(text.charAt(i) == pattern.charAt(j)){				
				i++;
				j++;
			}else{
				if(j != 0){
					j = temp[j-1];
				}else
				{
					i++;
				}
			}
		}
		if(j == pattern.length()){
			return true;
		}	
		
		return false;
	}
}
