package algorithms.array.medium;

import org.testng.annotations.Test;
/******
 * https://leetcode.com/problems/container-with-most-water/
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0). Find two lines, which, together with the x-axis forms a container, such that the container contains the most water.

	Notice that you may not slant the container.
	Example 1:
	Input: height = [1,8,6,2,5,4,8,3,7]
	Output: 49
	Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
	
	Example 2:
	Input: height = [1,1]
	Output: 1
	
	Example 3:
	Input: height = [4,3,2,1,4]
	Output: 16
	
	Example 4:
	Input: height = [1,2,1]
	Output: 2
	 
	
	Constraints:
	
	n == height.length
	2 <= n <= 105
	0 <= height[i] <= 104
 *
 */

public class Array23_ContainerWithMostWater {
	@Test
	private void test() {
		int[] input= {1,8,6,2,5,4,8,1000,1000};
		System.out.println(maxArea_BruteForce(input));
		System.out.println(maxArea_TwoPointer(input));

	}
	
	    public int maxArea_BruteForce(int[] height) {
	        int maxarea = 0;
	        for (int i = 0; i < height.length; i++)
	            for (int j = i + 1; j < height.length; j++)
	                maxarea = Math.max(maxarea, Math.min(height[i], height[j]) * (j - i));
	        return maxarea;
	    }
	    
	    public int maxArea_TwoPointer(int[] height) {
	        int maxarea = 0, l = 0, r = height.length - 1;
	        while (l < r) {
	            maxarea = Math.max(maxarea, Math.min(height[l], height[r]) * (r - l));
	            if (height[l] < height[r])
	                l++;
	            else
	                r--;
	        }
	        return maxarea;
	    }
	
}
