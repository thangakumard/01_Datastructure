package algorithms.string;

import java.util.*;
import org.testng.annotations.*;
import org.testng.*;


public class String16_removeDuplicates {
	
	@Test
	private void test() {
		char[] input = "We love Java".toCharArray();
		;
		removeDuplicates(input);
	}

		private void removeDuplicates(char[] str){
	      Set<Character> targetSet = new HashSet<Character>();
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
