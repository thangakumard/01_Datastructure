package algorithms.companies.amazon;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.testng.annotations.Test;



/*
 * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
 * 
 * 
 * In a list of songs, the i-th song has a duration of time[i] seconds. 

Return the number of pairs of songs for which their total duration in seconds is divisible by 60.
Formally, we want the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.

 

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
		Assertions.assertThat(numPairsDivisibleBy60(input_01)).isEqualTo(3);

		int[] input_02 = {60,60,60};
		Assertions.assertThat(numPairsDivisibleBy60(input_02)).isEqualTo(3);

		int[] input_3 = {418,204,77,278,239,457,284,263,372,279,476,416,360,18};
		Assertions.assertThat(numPairsDivisibleBy60(input_3)).isEqualTo(1);

		int[] input_4 = {336,24,100,342,274,11,43,22,416,138,384,386,70,265,59,253,344,435,400,296,192,143,311,424,315,63,420,254,493,431,32,394,178,51,378,335,265,92,335,325,25,355,258,298,390,399,393,114,149,62,299,471,286,204,163,214,15,272,315,212,272,437,339,193,125,394,62,188,154,150,109,294,228,200,459,42,469,132,37,460,143,1,144,127,398,82,370,464,14,85,321,358,205,14,264,289,183,93,56,126,413,140,441,446,445,378,258,119,385,226,8,93,476,265,115,86,360,92,396,407,458,58,65,397,381,32,228,37,319,220,73,328,162,458,231,219,481,387,423,256,252,36,309,395,471,4,225,146,188,182,347,82,21,292,91,144,387,263,206,452,197,192,324,257,370,28,440,180,294};
		Assertions.assertThat(numPairsDivisibleBy60(input_4)).isEqualTo(245);
	}

	public int numPairsDivisibleBy60(int[] time) {

		int []remainderCounter = new int[60];
		int res = 0;

		for(int t : time){
			int val = t % 60;

			if(val == 0){
				res += remainderCounter[0];
			}else{
				res += remainderCounter[60-val];//add the pair remainder counts to the result
			}

			remainderCounter[val]++;
		}

		return res;
	}
	
//	public int numPairsDivisibleBy60(int[] time) {
//
//		int pairs = 0;
//		for(int i=0; i< time.length; i++) {
//			time[i] = time[i]%60;
//		}
//
//		Arrays.sort(time);
//
//		int start = 0;
//		int end = time.length-1;
//
//		for(int i=0; i<= time.length-1; i++) {
//			start = i;
//			end = time.length-1;
//			while(start < end) {
//				if(time[start] + time[end] == 60 || time[start] + time[end] == 0) {
//					pairs++;
//				}else if(time[start] + time[end] < 60) break;
//				end--;
//			}
//		}
//
//		return pairs;
//
//    }
//
//	 public int numPairsDivisibleBy60_02(int[] time) {
//	        int c[]  = new int[60], res = 0;
//	        for (int t : time) {
//	            res += c[(600 - t) % 60];
//	            c[t % 60] += 1;
//	        }
//	        return res;
//	    }

}
