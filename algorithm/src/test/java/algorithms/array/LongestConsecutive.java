package algorithms.array;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LongestConsecutive {
	
	@Test
	public void longestSequence(){
		int[] a = {1,2,3,4,5,2,3,9,10,11,12,13,14};
		Assert.assertEquals(consecutive(a),8);
	}

	public int consecutive(int[] a) {

		int s = 0, f = 0;
		for (int i = 0; i < a.length-1; i++) {
			if(a[i] < a[i+1])
			{
				f++;
			}
			else{
				f = 1;
			}
			if(f > s){
				s = f;
			}
		}
		return s;
	}
}
