package arrayAlgorithms;

import org.testng.annotations.Test;

public class Array05_LeadersInTheArray {
	
	/*
	 * http://www.geeksforgeeks.org/leaders-in-an-array/
	 */
	
	@Test
	//Time complexity O(n^2)
	public void approach1(){
		
		System.out.println("Approach1 : ");
		int[] a = { 1,4,10,2,6,9};
		int j = 0;
		
		for(int i=0; i< a.length; i++){
			for(j = i+1; j < a.length; j++){
				if(a[i] <= a[j]){
					break;
				}
			}
			if(j == a.length){				
				System.out.println(a[i]);
			}
		}
	}

	@Test
	//Time complexity O(n)
	public void approach2(){
		System.out.println("Approach 2 :");
		int[] a = { 1,4,10,2,6,9};
		int maxValue = a[a.length-1];
		
		System.out.println(maxValue);
		
		for(int i=a.length-2; i >= 0;i--){
			
			if(maxValue < a[i]){
				maxValue = a[i];
				System.out.println(a[i]);
			}
		}	
	}
	
}
