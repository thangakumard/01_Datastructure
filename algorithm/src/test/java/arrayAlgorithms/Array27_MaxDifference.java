package arrayAlgorithms;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/maximum-difference-between-two-elements/
public class Array27_MaxDifference {
	
	
	@Test
	public void approach01(){
		
		int a[]  = {2,3,7,8,9,1,4};
		int size = a.length;
		int maxDiff = Integer.MIN_VALUE;
		int minElement = a[0];
		int difference = 0;
		
		for(int i=1; i < size; i++){
				
			if(a[i] >= minElement){
				difference = a[i] - minElement;
				if(difference > maxDiff){
					maxDiff = difference;
				}
			}
			else
			{
				minElement = a[i]; 
			}
		}
		
		System.out.println("Maximum difference is :" + maxDiff);
		
	}

}
