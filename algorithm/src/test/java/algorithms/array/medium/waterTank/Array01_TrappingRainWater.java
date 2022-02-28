package algorithms.array.medium.waterTank;

import org.testng.annotations.Test;

/******
 * 
 * https://leetcode.com/problems/trapping-rain-water/
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
 * compute how much water it can trap after raining.

	Example 1:
	Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
	Output: 6
	Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. 
	In this case, 6 units of rain water (blue section) are being trapped.
	
	Example 2:
	Input: height = [4,2,0,3,2,5]
	Output: 9
	
	Constraints:
	n == height.length
	0 <= n <= 3 * 104
	0 <= height[i] <= 105
 *
 */

public class Array01_TrappingRainWater {
	
	@Test
	public void test() {
		int[] height = new int[] {0,1,0,2,1,0,1,3,2,1,2,1};
		System.out.println(trap(height)); 
	}

	 public int trap(int[] height) {
	        if(height.length == 0) return 0;
	        int left = 0, right = height.length - 1, answer = 0;
	        int maxLeft = height[left], maxRight = height[right];
	        
	        while(left < right){
	            if(maxLeft < maxRight){
	                answer += maxLeft - height[left];
	                left++;
	                maxLeft = Math.max(maxLeft, height[left]);
	            }
	            else{
	                answer += maxRight - height[right];
	                right--;
	                maxRight = Math.max(maxRight, height[right]);
	            }
	        }
	        
	        return answer;
	    }
}
