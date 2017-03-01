package arrayAlgorithms;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/reverse-an-array-without-affecting-special-characters/
public class Array33_ReverseArraySkipSPLChar {

	@Test
	public void approach01(){

		String s = "abc,d!f#g";
		char[] str = s.toCharArray();
		int size = s.length();
		int l =0, r = size -1;
		char temp = ' ';
		
		while(l < r){
			if(!isAlphabet(str[l])){
				l++;
			}
			else if(!isAlphabet(str[r])){
				r--;
			}
			else
			{
				temp = str[l];
				str[l] = str[r];
				str[r] = temp;
				l++;
				r--;
			}
		}
		
		for(int i=0; i < str.length; i++){
			System.out.println(str[i]);
		}

	}


	public boolean isAlphabet(char c){
		return((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
	}

}
