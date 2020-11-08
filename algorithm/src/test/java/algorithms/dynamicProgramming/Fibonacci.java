package algorithms.dynamicProgramming;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Fibonacci {

	
	/**
	 * Fibonacci of 5 is
	 * 0, 1, 1, 2, 3, 5 
	 */
	@Test
	public void Test(){
		int result = fibDP(5);
		System.out.println(result);
		Assert.assertEquals(result, 5);
	}

	public int fibDP(int x) {
		int fib[] = new int[x + 1];
		fib[0] = 0;
		fib[1] = 1;
		for (int i = 2; i < x + 1; i++) {
			fib[i] = fib[i - 1] + fib[i - 2];
		}
		return fib[x];
	}
}
