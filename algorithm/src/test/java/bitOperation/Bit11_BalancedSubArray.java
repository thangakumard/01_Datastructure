package bitOperation;
import java.util.HashMap;

import org.testng.annotations.Test;
/***
 * 
 * https://leetcode.com/problems/contiguous-array/description/
 *
 */
public class Bit11_BalancedSubArray {

	@Test
	public void Test(){
		int[] input = new int[]{0,1,1,0,1,1,1,0};
		System.out.println(getBalancedArrayLength(input));
	}
	
	public int getBalancedArrayLength(int[] input){
		if(input == null || input.length == 0)
			return 0;
		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		int maxLength = 0, count =0;
		for(int i=0; i < input.length; i++){
			count = count + (input[i] == 1 ? 1 : -1);
			if(map.containsKey(count)){
				maxLength = Math.max(maxLength, i-map.get(count));
			}
			else{
				map.put(i, count);
			}
		}
		return maxLength;
	}
	
	
}
