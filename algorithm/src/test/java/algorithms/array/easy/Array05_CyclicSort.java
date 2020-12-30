package algorithms.array.easy;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/B1M5MlP1R7Q
 */
public class Array05_CyclicSort {
	
	private void cyclicSort(int[] nums) {
		
		int i=0, j =0;
		while(i < nums.length) {
			j = nums[i] -1;
			
			if(nums[i] != nums[j]) {
				int temp = nums[i];
				nums[i] = nums[j];
				nums[j] = temp;				
			}else {
				i++;
			}
		} 
		
	}

}
