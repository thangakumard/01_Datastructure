package algorithms.companies.amazon.amazon;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/discuss/interview-question/890290/
 */

public class Amazon_01_CutOffRank {
	
	@Test
	public void test() {
		int[] scores_01 = {100,50,50,25,200};
		System.out.println(cutOffRank(4,5,scores_01));
		
		int[] scores = {25,25,25,25,25,50};
		System.out.println(cutOffRank_wrong(4,6,scores));
		
		int[] scores_02 = {2,2,3,4,0};
		System.out.println(cutOffRank(4,5,scores_02));
	}

	public int cutOffRank_wrong(int cutOffRank, int num, int[] scores) {
	    int rank = 1;
	    int position = 1;
	    Arrays.sort(scores);
	    for (int i = num - 1; i >= 0; i--) {
	        if (i == num - 1 || scores[i] != scores[i + 1]) {
	            rank = position;
	            if (rank > cutOffRank || scores[i] == 0 ) return position - 1;
	        }
	        position++;
	    }
	    return num;
	}
	
	public int cutOffRank(int cutOffRank, int num, int[] scores) {
	
		int actualRank = 0;
		Arrays.sort(scores);
		
		for(int i=0; i< num; i++) {
			if(scores[i] != 0) {
				actualRank++;
				
			   if(i !=0 && scores[i] != scores[i-1] && actualRank > cutOffRank) {
					return actualRank-1;
				}
			}
		}
		return actualRank;
	}
	
}
