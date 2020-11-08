package algorithms.array;
import org.testng.annotations.Test;
public class Array53_ProductOfArray {

	@Test
	public void Test(){
		
		int[] input = new int[]{1,2,3,4};
		int[] output = productOfArray(input);
		System.out.println("Product Array is : ");
		for(int i: output){
			System.out.print(i + ",");
		}
	}
	
	
	private int[] productOfArray(int[] input){
		int[] result = new int[input.length];
		
		result[0] = 1;
		for(int i=1; i < input.length; i++){
			result[i] = result[i-1] * input[i-1];
		}
		
		int right = 1;
		for(int i = input.length-1; i >=0; i--){
			result[i] *= right;
			right *= input[i];
		}
		
		return result; 
	}
	
}
