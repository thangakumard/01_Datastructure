package arrayAlgorithms;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/maximum-difference-between-two-elements/
/***
Input : arr = {2, 3, 10, 6, 4, 8, 1}
Output : 8
Explanation : The maximum difference is between 10 and 2.

Input : arr = {7, 9, 5, 6, 3, 2}
Output : 2
Explanation : The maximum difference is between 9 and 7.

 *
 */
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
