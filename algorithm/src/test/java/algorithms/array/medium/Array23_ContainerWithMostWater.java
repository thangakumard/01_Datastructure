package algorithms.array.medium;

import org.testng.annotations.Test;

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
