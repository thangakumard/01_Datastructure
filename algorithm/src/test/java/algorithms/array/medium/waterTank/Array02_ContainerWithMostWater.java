package algorithms.array.medium.waterTank;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/container-with-most-water/
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 * Return the maximum amount of water a container can store.
 * Notice that you may not slant the container.
 *
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
 *
 * ZExample 2:
 * Input: height = [1,1]
 * Output: 1
 *
 * Constraints:
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 104
 */
public class Array02_ContainerWithMostWater {
    @Test
    public void test(){
        int[] input = new int[]{1,8,6,2,5,4,8,3,7};
        Assertions.assertThat(maxArea(input)).isEqualTo(49);
    }
    public int maxArea(int[] height) {
        int left = 0, right = height.length -1;
        int area = Integer.MIN_VALUE;

        while(left < right){
            int a = Math.min(height[left], height[right]) * (right-left);
            area = Math.max(area, a);

            if(height[left] < height[right]){
                left ++;
            }else{
                right--;
            }
        }
        return area;
    }




}
