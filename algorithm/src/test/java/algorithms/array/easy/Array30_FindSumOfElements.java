package algorithms.array.easy;

import java.util.HashSet;

import org.testng.annotations.Test;

//www.geeksforgeeks.org/write-a-c-program-that-given-a-set-a-of-n-numbers-and-another-number-x-determines-whether-or-not-there-exist-two-elements-in-s-whose-sum-is-exactly-x/
/*****
 * 
 * @author THANGAKUMAR
 *
 */
public class Array30_FindSumOfElements {

	@Test
	//Time complexity O(n^2)
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
	
	@Test
	//Time Complexity O(n)
	public void printpairs()
    { 
		int A[] = {1, 8, 45, 6, 10, 8};
        int n = 16;
        printpairs(A,  n);
    }
    
	private void printpairs(int arr[],int sum)
    {       
        HashSet<Integer> s = new HashSet<Integer>();
        for (int i=0; i<arr.length; ++i)
        {
            int temp = sum-arr[i];
            // checking for condition
            if (temp>=0 && s.contains(temp))
            {
                System.out.println("Pair with given sum " +
                                    sum + " is (" + arr[i] +
                                    ", "+temp+")");
            }
            s.add(arr[i]);
        }
    }
	
}
