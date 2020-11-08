package algorithms.array;
import org.testng.annotations.Test;
/****
 * 
 * https://leetcode.com/problems/powx-n/description/
 
    Implement pow(x, n), which calculates x raised to the power n (xn).
	
	Example 1:
	
	Input: 2.00000, 10
	Output: 1024.00000
	Example 2:
	
	Input: 2.10000, 3
	Output: 9.26100
	Example 3:
	
	Input: 2.00000, -2
	Output: 0.25000
	Explanation: 2-2 = 1/22 = 1/4 = 0.25

 */
public class Array49_Pow {
	
	@Test
	public void TestPower(){
		System.out.println(power(1.00000, -2147483648));
		System.out.println(power(2.00000,10));
	}
	
	
	public double power(double x, int n){
		
		long N = n;
		if( N < 0){
			x = 1/x;
			N = -N;
		}
		double answer = 1;
		double currentValue =x;
		
		for(long i=N; i >0; i/=2){
			if(i%2 == 1){
				answer = answer * currentValue; // for ODD number
			}
			currentValue = currentValue * currentValue;
		}
		
		return answer;
	}

}
