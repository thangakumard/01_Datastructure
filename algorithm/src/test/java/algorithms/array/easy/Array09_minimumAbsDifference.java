package algorithms.array.easy;
import java.util.*;

import org.testng.annotations.Test;

public class Array09_minimumAbsDifference {

	@Test
	private void test() {
			 int[] input_01 = {4,2,1,3};
		     System.out.println(minimumAbsDifference(input_01));
		     int[] input_02 = {1,3,6,10,15};
		     System.out.println(minimumAbsDifference(input_02));
		     int[] input_03 = {4,2,1,3};
		     System.out.println(minimumAbsDifference(input_03));
	}
	
	 public List<List<Integer>> minimumAbsDifference(int[] arr) {
	        Arrays.sort(arr);
	        int min_diff = Integer.MAX_VALUE;
	        List<List<Integer>> result = new ArrayList<>();      
	        
	        for(int i=1; i< arr.length; i++){
	            int diff = Math.abs(arr[i-1] - arr[i]);
	            if(diff < min_diff){
	                result.clear();
	                result.add(Arrays.asList(arr[i-1], arr[i]));
	                min_diff = Math.min(min_diff, diff);
	            }else if(diff == min_diff){
	                result.add(Arrays.asList(arr[i-1], arr[i]));
	            }
	        }
	        
	        return result;
	    }
}
