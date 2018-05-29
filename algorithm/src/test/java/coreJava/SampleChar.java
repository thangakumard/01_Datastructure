package coreJava;

import org.testng.annotations.Test;

public class SampleChar {

	@Test
	public void SampleChar(){
		
		String s = "1243";
		char[] input = s.toCharArray();
		
		System.out.println("Subtract by 0");
		for(char c: input){
			System.out.print(c - '0');
		}
		System.out.println("");
		System.out.println("Subtract by 1");
		for(char c: input){
			System.out.print(c - '1');
		}
		System.out.println("");
		System.out.println(5 + "" + 1);	
		
	}
	
	@Test
	public void isAlphaNumeric(){
		System.out.println("Character.isLetterOrDigit('C') : " + Character.isLetterOrDigit('C'));
		System.out.println("Character.isLetterOrDigit('9') : " +Character.isLetterOrDigit('9'));
		System.out.println("Character.isLetterOrDigit('&') : " + Character.isLetterOrDigit('&'));
		System.out.println("Character.isLetterOrDigit(' ') : " + Character.isLetterOrDigit(' '));
	}
}
