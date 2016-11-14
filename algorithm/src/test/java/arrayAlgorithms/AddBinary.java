package arrayAlgorithms;

import org.junit.Assert;
import org.testng.annotations.Test;

public class AddBinary {

	/*
	//@Test
	public void Approach1(){
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
	
	@Test
	public void Approach2(){
		String i = "1001";
		String j = "11";
		
		Assert.assertEquals("1100", addBitString(i,j));
	}
	
	
	
	private String addBitString(String a, String b){
		
		String result = "";
		int L1 = a.length();
		int L2 = b.length();
		
		if(a.length() < b.length()){
			for(int i=0; i < L2-L1; i++){
				a= '0' + a;
			}
		}

		if(b.length() < a.length()){
			for(int i=0; i < L1-L2; i++){
				b= '0' + b;
			}
		}
		
		int length = a.length();
		
		int carry = 0;
		
		for(int i =0; i< a.length(); i++){
			int x = a.charAt(i) - '0';
			int y = b.charAt(i) - '0';
			
			int sum = (x ^ y ^ carry) + '0';
			
			result =Integer.toString(sum) + result;
			
			carry = (x & y) | (y & carry) | (x & carry);
		}

		if(carry == 1)
			result = '1' + result;
		
		return result;
	}
	
*/
}
