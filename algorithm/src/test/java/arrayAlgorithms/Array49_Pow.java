package arrayAlgorithms;
import org.testng.annotations.Test;
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
