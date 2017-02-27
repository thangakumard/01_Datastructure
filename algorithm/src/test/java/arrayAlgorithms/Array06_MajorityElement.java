package arrayAlgorithms;

import org.testng.annotations.Test;

public class MajorityElement {

	/*
	 * Majority Element: A majority element in an array A[] of size n is an element that appears more than n/2 times (and hence there is at most one such element).
	 * http://www.geeksforgeeks.org/majority-element/
	 */
	@Test
	//Time complexity O(n^2)
	public void approach1(){
		
		int[] a = {2,2,3,4,4,2,4,4,3,4,4,4};
		
		int n = a.length/2;
		int m =0;
		
		for(int i=0; i < a.length ; i++){
			for(int j=i+1; j < a.length; j++){
				if(a[i] == a[j])
				{
					m++;					
				}				
			}
			if(n <= m){
				System.out.println("Approach 1 : Majority Element is :" + a[i]);
				break;
			}
		}
	}
	
	
	
	/*
	 * printMajority (a[], size)
		1.  Find the candidate for majority
		2.  If candidate is majority. i.e., appears more than n/2 times.
		       Print the candidate
		3.  Else
       			Print "NONE"
	 */
	@Test
	//Moore’s Voting Algorithm
	public void approach2(){
		
		System.out.println( "Approach 2");
		int[] a = {2,2,3,4,4,2,4,4,3,4,4,4};
		int size = a.length;
		
		int cand = findCandidate(a,size);
		
		if(isMajority(a, size, cand)){
			System.out.println(cand);
		}
		else
			System.out.println("No Majority Element !");
		
	
	}

	int findCandidate(int[] a, int size){
		int maj_index = 0; 
		int count = 1;
		int i =0;
		
		for(i=1; i < size ; i++){
			if(a[maj_index] == a[i]){
				count++;
			}
			else
				count--;
			
			if(count == 0){
				maj_index = i;
				count = 1;
			}
		}
		return a[maj_index];
	}
	
	boolean isMajority(int[] a, int size, int cand){
		int count = 0;
		for(int i=0 ; i < size; i ++){
			
			if(a[i] == cand)
				count++;
		}
		if(count > size/2)
			return true;
		else 
			return false;
	}
	
	
	
}
