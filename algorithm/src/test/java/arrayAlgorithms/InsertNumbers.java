package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

public class InsertNumbers {
	
	@Test
	public void getPosition()
	{
		int[] a = {2,3,4,5,6,7,8};
		Assert.assertEquals(getNumberPosition(a,1), 1);
	}

	@SuppressWarnings("unused")
	public int getNumberPosition(int[] number, int i) {
		int j = 0;
		int start = 0, end = number.length;
		int m = (start + end) / 2;

		if (number == null) 
			return 0;

			if (i >= number[number.length - 1])
				return number.length - 1;

			while (m > 0 && m < number.length - 1) {
				if (i > number[m]) {

					start = m + 1;
					m = (start + end) / 2;
				}
				else if(i < number[m]){
				
					end = m -1;
					m = (start + end)/2;
				}
				else break;
			}
		return m+1;
	}
}
