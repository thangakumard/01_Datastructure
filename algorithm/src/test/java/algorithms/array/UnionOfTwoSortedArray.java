package algorithms.array;

import org.testng.annotations.Test;

public class UnionOfTwoSortedArray {

	/*Good explanation is in the below article
	 * http://articles.leetcode.com/here-is-phone-screening-question-from/
	 * http://stackoverflow.com/questions/15485641/hash-table-vs-sorted-array-which-to-use 
	 */
	@Test
	public void UnionOfSprtedArray(){
		
		int[] a = {1,2,3,4,5,6,7,8,9};
		int[] b = {1,2,3,4,11,12,13};
		
		int m = a.length;
		int n = b.length;
		int i=0 , j = 0;
		
		while(i < m && j <n){
			if(a[i] < b[j]){
				System.out.println(a[i]);
				i++;
			}
			else if(a[i] > b[j]){
				System.out.println(b[j]);
				j++;
			}
			else
			{
				System.out.println(a[i]);
				i++;
				j++;
			}
		}
		
		while(i < m){
			System.out.println(a[i]);
			i++;
		}
			
		while(j < n){
			System.out.println(b[j]);
			j++;
		}
	}
}
