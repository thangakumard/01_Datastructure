package arrayAlgorithms;

import org.testng.annotations.Test;

public class MinimumDistanceBetween2Numbers {
	
	/**
	 * http://www.geeksforgeeks.org/find-the-minimum-distance-between-two-numbers/
	 * Time complexity is O(n^2)
	 */
	@Test
	public void approach1(){
		
		int[] a = {3,5,6,7,8,1,2,5,0,1,2,3};
		int x =5;
		int y = 1;
		
		int min_distance = Integer.MAX_VALUE;
		
		for(int i=0; i < a.length; i ++){
			
			for(int j=i+1; j <a.length; j++){
				
				if((x == a[i] && y == a[j]) ||
						(y == a[i] && x== a[i]) &&
							min_distance > Math.abs(i-j)){
					min_distance = Math.abs(i-j);
				}
			}
		}
		
		System.out.println("Approach1 Minimum Distance :" + min_distance);		
	}
	
	
	/*
	 * Time Complexity: O(n)
	 */
	@Test
	public void approach2(){
		
		int[] a = {3,5,6,7,8,1,2,5,0,1,2,3};
		int x=5;
		int y=1;
		
		int min_distance = Integer.MAX_VALUE;
		int prev = 0;
		int i=0;
		
		for(i=0; i < a.length; i++){
			if(a[i] == x || a[i] == y)
			{
				prev =i;
				break;
			}
		}
		
		for(; i< a.length; i++){
			if(a[i] == x || a[i] == y){
				
				if(a[prev] != a[i] && min_distance > Math.abs(prev - i)){
					min_distance = Math.abs(prev - i);
				}
				else{
					prev = i;
				}
			}			
		}
		
		System.out.println("Approach2 Minimum Distance :" + min_distance);	
	}

}
