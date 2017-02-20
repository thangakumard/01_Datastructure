package bitOperation;

import org.testng.annotations.Test;

/*
 * Given a positive integer n, 
 * Find out the count of positive integers i 
 * such that  0<= i <= n and n+i == n^i
 * 
 * Example
 * Input n=7;
 * Logic : 7+0 == 7^0
 * output = 1;
 * 
 *  input n=12
 *  Logic : 12+i == 12^ i hold only for i = 0,1,2,3
 *  for i=0 , 12+0 == 12^0
 *  for i=1 , 12+1 == 12^1
 *  for i=2 , 12+2 == 12^2
 *  for i=3 , 12+3 == 12^3
 */
public class EqualSumAndXOR {

	@Test
	public void simpleSolution(){
		
		int n = 12;		
		int counter = 0;		
		for(int i=0 ; i < n; i++)
		{
			if((n+i) == (n^i))
			{
				counter++;
			}
		}				
		System.out.println(counter);
	}
	
	/*
	 * We know (a+b) == (a^b) + (a&b)
	 * so if(a&b) ==0 => (ab+b) == (a^b)
	 * We need to find the pairs such that n&i =0
	 * To identify such combinations effective way is ( 2 ^ number of unset bits in n)
	 * */
	@Test
	public void EffectiveSolution()
	{
		int n = 12;
		int unsetbits = 0;
		while(n > 0)
		{
			if((n&1) == 0)
				unsetbits ++;
			n = n>>1;
		}
		
		System.out.println(1 << unsetbits);
	}
}
