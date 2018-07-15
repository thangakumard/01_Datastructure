package arrayAlgorithms;

import org.testng.annotations.Test;

/************
 * https://www.geeksforgeeks.org/replace-every-element-with-the-greatest-on-right-side/
 * Given an array of integers, replace every element with the next greatest element 
 * (greatest element on the right side) in the array. 
 * Since there is no element next to the last element, replace it with -1. 
 * For example, if the array is {16, 17, 4, 3, 5, 2}, 
 * then it should be modified to {17, 5, 5, 5, 2, -1}.
 *
 */

public class Array08_ReplaceWithGreatestElement {

	@Test
	//Time complexity is O(n^2)
	public void approach1(){
		System.out.println("Approach 1");
		int[] a ={16, 17, 4, 3, 5, 2};
		int max = 0;
		
		for(int i=0; i < a.length; i++){
			max = 0;		
			
			for(int j=i+1; j <a.length; j++ ){				
				if(max < a[j]){
					max = a[j];
				}				
			}
			
			if(i == a.length -1) a[i] = -1;
			else a[i] = max;
		}
		
		for(int i=0; i < a.length; i++){
			System.out.println(a[i]);
		}
	}
	
	@Test
	public void approach2(){
		System.out.println("Approach 2");
		int[] a ={16, 17, 4, 3, 5, 2};
		int max = -1;
		int newMax = 0;
		
		for(int i=a.length-1; i >=0 ; i--){
			if(i == 0){
				a[i] = max;
			}
			else if(max < a[i]){
				newMax = a[i];
				a[i] = max;
				max = newMax;
			}
			else
			{
				a[i] = max;
			}
		}
		for(int i=0; i < a.length; i++){
			System.out.println(a[i]);
		}
	}
	
}
