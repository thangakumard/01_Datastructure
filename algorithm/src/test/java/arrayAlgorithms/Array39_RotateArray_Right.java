package arrayAlgorithms;
import org.testng.annotations.*;

public class Array39_RotateArray_Right {

	@Test
	public void Test(){
		
		int[] input = {10,20,30,40,50,60};
		rightRotate(input, 3);
		for(int i=0; i < input.length ; i++){
			System.out.println(input[i]);
		}
	}
	
	public void rightRotate(int[] input, int d){
		
		reverse(input, 0, input.length-1-d);
		reverse(input, input.length-d, input.length-1);
		reverse(input,0, input.length-1);
	}
	
	private void reverse(int[] input, int start, int end){
		
		int temp = 0;
		while(start < end){			
			temp = input[start];
			input[start] = input[end];
			input[end] = temp;
			
			start++;
			end--;
		}
		
	}
}
