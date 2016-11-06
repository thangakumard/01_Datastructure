package arrayAlgorithms;

import org.junit.Assert;
import org.testng.annotations.Test;

public class AddBinary {
	
	@Test
	public void binarySum(){
		int i = 1001;
		int j = 11;
		
		Assert.assertEquals("1100", add(i,j));
	}

	private String add(int i, int j) {

		if (i == 0)
			return Integer.toString(j);
		if (j == 0)
			return Integer.toString(i);

		int x = 0, y = 0, z = 0, sum = 0;
		StringBuilder total = new StringBuilder(); 
		while (i > 0 || j > 0) {
			
			x = i % 10;
			y = j % 10;
			
			sum =  x + y + z; 
			if (sum >= 2){
				z  = 1;
				total.append(sum - 2);
			}
			else if(sum < 2){
				z = 0;
				total.append(sum);
			}
				
			i = i / 10;
			j = j / 10;
		}

		String result = total.reverse().toString();
		
		System.out.println("Result is : " + result);
		return result;

	}

}
