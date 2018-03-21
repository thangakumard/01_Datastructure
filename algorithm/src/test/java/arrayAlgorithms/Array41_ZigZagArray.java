package arrayAlgorithms;

import org.testng.annotations.*;

public class Array41_ZigZagArray {
	
	@Test
	public void test(){
		int[] input = {10,20,30,40,50,60,70,80,90,100};
		zigZag(input);
		for(int i=0; i < input.length; i++){
			System.out.print(input[i] + ", ");
		}
		System.out.println();
	}
	
	public void zigZag(int[] input){
		
		int i = 0;
		int temp = 0;
		boolean flip = true;
		while(i < input.length-1){
			if(flip){
				if(input[i] > input[i+1]){				
					temp = input[i];
					input[i] = input[i+1];
					input[i+1] = temp;
				}
			}
			else{
				if(input[i] < input[i+1]){
					temp = input[i];
					input[i] = input[i+1];
					input[i+1] = temp;
				}
			}
			flip = !flip;
			i++;
		}
		
	}
}
