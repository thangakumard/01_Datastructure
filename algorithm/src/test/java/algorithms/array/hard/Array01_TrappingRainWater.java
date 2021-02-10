package algorithms.array.hard;

public class Array01_TrappingRainWater {

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
