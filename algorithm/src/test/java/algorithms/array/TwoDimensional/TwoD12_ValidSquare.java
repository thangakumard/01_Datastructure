package algorithms.array.TwoDimensional;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TwoD12_ValidSquare {
	
	@Test
	private void test() {
		int[] p1 = new int[] {1134, -2539};
		int[] p2 = new int[] {492,-1255};
		int[] p3 = new int[] {-792,-1897};
		int[] p4 = new int[] {-150,-3181};
		
		Assert.assertEquals(validSquare(p1,p2,p3,p4), true);
		
	}

	public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        long[] lengths ={length(p1,p2),length(p2,p3),
                         length(p3,p4),length(p4,p1),
                         length(p1,p3),length(p2,p4)};
        long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
        for(long l: lengths){
            max = Math.max(max,l);
            min = Math.min(min,l);
        }
        int min_count = 0, max_count = 0;
        for(long l: lengths){
            if(min == l) min_count++;
            if(max == l) max_count++;
        }
        
        return (max_count == 2 && min_count == 4);
    }
    
    private long length(int[] p1, int[] p2){
        return (long)Math.pow((p1[0]-p2[0]),2) + (long)Math.pow((p1[1]-p2[1]),2);
    }
}
