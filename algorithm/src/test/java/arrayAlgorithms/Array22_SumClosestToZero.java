package arrayAlgorithms;

import org.testng.annotations.Test;

public class Array22_SumClosestToZero {

	@Test
	public void approach01(){
		
		int[] input = {1, 60, -10, 70, -80, 85};
		
		int minValue = Math.abs(input[0] + input[1]);
		int a =0, b = 0;
		
		for(int i =0; i < input.length;i++){
			
			for(int j=i+1; j < input.length;j++){
					int absValue = Math.abs(input[i] + input[j]);
				if(absValue < minValue){
					minValue = absValue;
					a = input[i];
					b = input[j];
				}
			}
		}
		
		System.out.println("Values near by 0 are :" + a + " and " + b);
	}
}
