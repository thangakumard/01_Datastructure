package algorithms.string;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;


public class String16_removeDuplicates {
	
	@Test
	private void test() {
		char[] input = "We love Java".toCharArray();
		removeDuplicates(input);
		char[] expected = new char[input.length];
		expected[0] = 'W';expected[1] = 'e';expected[2] = ' ';expected[3] = 'l';
		expected[4] = 'o';expected[5] = 'v';expected[6] = 'J';expected[7] = 'a';
		Assertions.assertThat(input).isEqualTo(expected);
	}

		private void removeDuplicates(char[] str){
	      Set<Character> targetSet = new HashSet<>();
	      int write =0;
	      for(int read=0; read< str.length; read++){
	        if(!targetSet.contains(str[read])){
	            targetSet.add(str[read]);
	            str[write] = str[read];
	            write++;
	          }
	        }
	        while(write < str.length){
	          str[write] = Character.MIN_VALUE;
	          write++;
	        }
	        System.out.println(str);
	      }
}
