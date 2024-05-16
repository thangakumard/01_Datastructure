package algorithms.array.medium.slidingWindow;
import java.util.*;

import org.testng.annotations.Test;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/R1XgYW2PZzK
 */
public class SlidingWindow03_ProductLessThanKReturnList {

	 @Test
	 public  void test() {
		    System.out.println(this.findSubarrays(new int[] { 2, 5, 3, 10 }, 30));
		    System.out.println(this.findSubarrays(new int[] { 8, 2, 6, 5 }, 50));
		  }
	
	public  List<List<Integer>> findSubarrays(int[] nums, int target) {
	    List<List<Integer>> result = new ArrayList<>();
	     if(target <= 1) return new ArrayList<>();
	        
	        int product = 1;        
	        int left = 0, right = 0;
	        
	        while(right < nums.length){
	            
	            product *= nums[right];
	            
	            while(product >= target){
	                product /= nums[left];
	                left++;
	            }
	            List<Integer> tempList = new LinkedList<>();
	            for (int i = right; i >= left; i--) {
	              tempList.add(0, nums[i]);
	              result.add(new ArrayList<>(tempList));
	            }
	            right++;
	        }
	        return result;
	    }
}
