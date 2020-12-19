package coreJava;

import org.testng.annotations.Test;

public class SampleChar {

	@Test
	public void SampleChar(){
		
		String s = "1243";
		System.out.println("s.charAt(1) :" + s.charAt(1));
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
	
	@Test
	private void charIntValue() {
		
	
		
		
		
		System.out.println("a :"+ Character.getNumericValue('a'));
		System.out.println("b :"+ Character.getNumericValue('b'));
		System.out.println("c :"+ Character.getNumericValue('c'));
		System.out.println("d :"+ Character.getNumericValue('d'));
		System.out.println("e :"+ Character.getNumericValue('e'));
		System.out.println("f :"+ Character.getNumericValue('f'));
		System.out.println("g :"+ Character.getNumericValue('g'));
		System.out.println("h :"+ Character.getNumericValue('h'));
		
		
		System.out.println("A :"+ Character.getNumericValue('A'));
		System.out.println("B :"+ Character.getNumericValue('B'));
		System.out.println("C :"+ Character.getNumericValue('C'));
		System.out.println("D :"+ Character.getNumericValue('D'));
		System.out.println("E :"+ Character.getNumericValue('E'));
		System.out.println("F :"+ Character.getNumericValue('F'));
		System.out.println("G :"+ Character.getNumericValue('G'));
		System.out.println("H :"+ Character.getNumericValue('H'));
		
		int[] value = new int[26];
		value[0] = 'a';
		value[1] = 'b';
		value[2] = 'c';
		value[3] = 'd';
		value[4] = 'e';
		
		for(int i=0; i < 5; i++) {
			System.out.println(value[i]);
		}
		
		value[0] = 'a';
		value[1] = 'b';
		value[2] = 'c';
		value[3] = 'd';
		value[4] = 'e';
		
		for(int i=0; i < 5; i++) {
			System.out.println(value[i]- 'a');
		}
		
	}
	
}
