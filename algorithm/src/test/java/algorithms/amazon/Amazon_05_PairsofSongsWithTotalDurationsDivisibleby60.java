package algorithms.amazon;

import java.util.Arrays;

import org.junit.Assert;
import org.testng.annotations.Test;



/*
 * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
 * 
 * 
 * In a list of songs, the i-th song has a duration of time[i] seconds. 

Return the number of pairs of songs for which their total duration in seconds is divisible by 60.  Formally, we want the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.

 

Example 1:

Input: [30,20,150,100,40]
Output: 3
Explanation: Three pairs have a total duration divisible by 60:
(time[0] = 30, time[2] = 150): total duration 180
(time[1] = 20, time[3] = 100): total duration 120
(time[1] = 20, time[4] = 40): total duration 60
Example 2:

Input: [60,60,60]
Output: 3
Explanation: All three pairs have a total duration of 120, which is divisible by 60.
 

Note:

1 <= time.length <= 60000
1 <= time[i] <= 500
 */
public class Amazon_05_PairsofSongsWithTotalDurationsDivisibleby60 {
	
	@Test
	private void test() {
		int[] input_01 = {30,20,150,100,40};
		Assert.assertEquals(numPairsDivisibleBy60_02(input_01), 3);
		
		int[] input_02 = {60,60,60};
		Assert.assertEquals(numPairsDivisibleBy60_02(input_02), 3);
		
		int[] input_3 = {418,204,77,278,239,457,284,263,372,279,476,416,360,18};
		Assert.assertEquals(numPairsDivisibleBy60(input_3), 1);
	}
	
	public int numPairsDivisibleBy60(int[] time) {
		
		int pairs = 0;
		int zeros = 0;
		
		for(int i=0; i< time.length; i++) {
			time[i] = time[i]%60;
		}
		
		Arrays.sort(time);
		
		int start = 0;
		int end = time.length-1;
		
		for(int i=0; i<= time.length-1; i++) {
			
					start = i;
					end = time.length-1;
					while(start < end) {
						if(time[start] + time[end] == 60 || time[start] + time[end] == 0) {
							pairs++;
						}else if(time[start] + time[end] < 60) break;
						end--;
					}
			 }
		
		return pairs;
        
    }

	 public int numPairsDivisibleBy60_02(int[] time) {
	        int c[]  = new int[60], res = 0;
	        for (int t : time) {
	            res += c[(600 - t) % 60];
	            c[t % 60] += 1;
	        }
	        return res;
	    }
}
