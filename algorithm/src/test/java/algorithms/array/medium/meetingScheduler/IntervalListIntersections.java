package algorithms.array.medium.meetingScheduler;

import java.util.*;

import org.testng.annotations.Test;

public class IntervalListIntersections {
	
	
	@Test
	private void test() {
		/*
		 * [[0,2],[5,10],[13,23],[24,25]]
			[[1,5],[8,12],[15,24],[25,26]]
		 */
		int[][] intervals1= {{0,2},{5,10},{13,23},{24,25}};
		int[][] intervals2= {{1,5},{8,12},{15,24},{25,26}};
		
		int[][] result = intervalIntersection(intervals1, intervals2);
		for(int[] interval: result) {
			System.out.print("{" + interval[0] + "," + interval[1] + "},");
		}
	}

public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        
        List<int[]> result = new ArrayList<>();
        
        int i =0, j =0;
        
        while(i < firstList.length && j < secondList.length){
            int intersect1 = Math.max(firstList[i][0], secondList[j][0]);
            int intersect2 = Math.min(firstList[i][1], secondList[j][1]);
            
            if(intersect1 <= intersect2)
                result.add(new int[]{intersect1,intersect2});
            
            if(firstList[i][1] < secondList[j][1]){
                i++;
            }else{
                j++;
            }
          
        }
        
        return result.toArray(new int[result.size()][]);
    }
}
