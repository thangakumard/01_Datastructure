package algorithms.array.easy;

import java.util.*;
import org.testng.annotations.*;

public class Array32_OddOccurrencesInArray {

	@Test
	private void test() {
		int[] input = new int[] {1,2,1,2,5,6,6};
		
		System.out.println(OddOccurrencesInArray(input));
	}
	
	private int OddOccurrencesInArray(int[] input) {
	
		int oddOccurance = input[0];
		
		
		for(int i=1; i< input.length; i++) {
			oddOccurance = oddOccurance ^ input[i];
		}
		
		return oddOccurance;
	}
}
