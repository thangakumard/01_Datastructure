package arrayAlgorithms;

import org.testng.annotations.Test;

//www.geeksforgeeks.org/write-a-c-program-that-given-a-set-a-of-n-numbers-and-another-number-x-determines-whether-or-not-there-exist-two-elements-in-s-whose-sum-is-exactly-x/

public class Array15_FindSumOfElements {

	@Test
	//Time complexity O(n)
	public void approach1(){
		
		int[] a = {1,4,10,6,24,-8};
		int sum = 16;
		int size = a.length;
		int total =0;
		int cnt = 0;
		
		for(int i=0; i< size; i++){
			for(int j=i+1; j <size; j++){
				if(total < sum || a[j] < 0){
					total = a[i] + a[j];
				}
				if(total == sum){
					total = 0;
					cnt++;
					break;
				}
			}
		}
		System.out.println("Number of sum elements : " + cnt);
	}
}
