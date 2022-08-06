package algorithms.array.hard;

/***
 * https://leetcode.com/problems/trapping-rain-water/submissions/
 *
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it can trap after raining.
 *
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
 * In this case, 6 units of rain water (blue section) are being trapped.
 */
public class Array02_TrappingRainWater<Integer> {
    public int trap(int[] height) {
        int left = 0, right = height.length-1;
        int left_max = height[left], right_max = height[right];
        int result = 0;

        while(left < right){
            if(height[left] < height[right]){
                if(height[left] >= left_max){
                    left_max = height[left];
                }else{
                    result += left_max - height[left];
                }
                left++;
            }else{
                if(height[right] >= right_max){
                    right_max = height[right];
                }else{
                    result += right_max - height[right];
                }
                right--;
            }

        }
        return result;
    }
}
