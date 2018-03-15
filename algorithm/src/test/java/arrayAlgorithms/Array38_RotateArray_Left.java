package arrayAlgorithms;

import org.testng.annotations.*;

public class Array38_RotateArray_Left {

	@Test
	public void Test(){
		int[] input = {10,20,30,40,50,60};
		int k = 2; //rotate numbers
		rotateArray(input, k);	
		
		for(int i=0; i< input.length; i++){
			System.out.println(input[i]);
		}
	}
	
	private void rotateArray(int[] input, int k){
		
		int j=input.length - k, temp = 0;
		
		for(int i=0; i< input.length && j < input.length; i++, j++){
			
			temp = input[i];
			input[i] = input[j];
			input[j] = temp;			
		}		
	}
	
}
