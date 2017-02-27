package arrayAlgorithms;

import org.testng.annotations.Test;

public class FindMissingElement {

	
	/** using formula
	 * 	total = n *(n+1) /2
	 * Subtract all the elements one by one from total.
	 * We will get the missing element 
	 * */ 
	//Time complexity O(n)
	@Test
	public void Approach1(){
		
		int[] a = {1,2,3,4,6,7,8,9};
		int n = a.length;
		
		//one element is missing so add 1 with n
		n = n+1;
		
		int total = n * ( n+1)/2;	
		
		for(int j = 0; j < a.length; j++){
			total -= a[j];
		}
		
		System.out.println("Missing Element is :" + total);
	}
	
	@Test
	public void Approach2(){
		int[] a = { 1,2,3,4,6,7,8,9};
		int n =a.length;
		 int i;
		    int x1 = a[0]; /* For xor of all the elements in array */
		    int x2 = 1; /* For xor of all the elements from 1 to n+1 */
		     
		    for (i = 1; i< n; i++)
		        x1 = x1^a[i];
		            
		    for ( i = 2; i <= n+1; i++)
		        x2 = x2^i; 
		System.out.println(x1 ^ x2);
	}
}
