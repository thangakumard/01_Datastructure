package algorithms.array.greedy;

import java.util.Arrays;

public class Greedy02_FindMinArrowShots {
    /***
     * Time complexity : O(n log n),
     * Space Complexity: O(1) extra space beyond the sort.
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points) {
        if(points == null || points.length == 0)
            return 0;
        int arrows = 1;
        Arrays.sort(points, (p1, p2) -> Long.compare((long)p1[1], (long)p2[1]));
        int lastArrowEndPos = points[0][1];
        for(int i=1; i < points.length; i++){
            if(lastArrowEndPos < points[i][0]){ //No overlap = - need an additional arrow
                arrows++;
                lastArrowEndPos = points[i][1];
            }
        }
        return arrows;
    }
}
