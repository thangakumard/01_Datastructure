package algorithms.bitOperation;

import org.testng.annotations.Test;

public class Bit08_ANDofNumbersRange {

	@Test
	public void test(){
		int result = 1;
		System.out.println("AND of numbers in the range :");
		System.out.println(AndAll(5,7));
	}
	
	private int AndAll(int start, int end){
		while(start < end){
			end = end & end-1;
		}
		return start & end;
	}
	
}
