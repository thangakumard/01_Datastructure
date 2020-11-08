package algorithms.array;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/write-a-program-to-reverse-an-array-or-string/
/*****
 * 
	 Input  : arr[] = {1, 2, 3}
	 Output : arr[] = {3, 2, 1}
	
	 Input :  arr[] = {4, 5, 1, 2}
	 Output : arr[] = {2, 1, 5, 4}
 *
 */
public class Array28_ReverseArray {

	@Test
	//Time complexity O(n)
	public void approach01(){

		int[] a ={1,2,3,4,5,6,7,8,9};

		int mid = a.length/2;
		int j= a.length-1;
		int temp = 0;

		for(int i=0; i < mid ;i++){
			
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				j--;
		}
		
		System.out.println("Approach 01 :");
		
		for(int i=0; i < a.length;i++){
			System.out.println(a[i]);
		}

	}
	
	@Test
	//Time complexity O(n)
	public void approach02(){
		int[] a ={1,2,3,4,5,6,7,8,9};

		System.out.println("Approach 02 :");
		reverse(a, 0, a.length-1);
		

		for(int i=0; i < a.length;i++){
			System.out.println(a[i]);
		}
		
	}
	
	public void reverse(int[] input, int left, int right){
		
		int temp = 0;
		if(left < right){			
			temp = input[left];
			input[left] = input[right];
			input[right] = temp;
		}
		else
			return;
		reverse(input, left+1, right-1);
	}



}
