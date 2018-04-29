package arrayAlgorithms.search;

import org.testng.annotations.Test;

public class excersice {

	@Test
	public void sample1(){
		int[] input = {10,20,30,40,50,60};
		int[] result = rotateLeft(input, 2);
		for(int i=0; i < input.length; i++){
			System.out.println(result[i]);
		}
	}
	
	private int[] rotateLeft(int[] input,int k){
		int[] result = new int[input.length];
		int n = input.length;
		int mod = k % n;
		
		for(int i=0; i < n; i++){
			result[i] = input[(i+mod)%n];
		}
		
		return result;
	}
	
	
}
