package algorithms;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;
/******
 * 
 https://www.geeksforgeeks.org/c-program-find-gcd-hcf-two-numbers/
 GCD (Greatest Common Divisor) or HCF (Highest Common Factor) of two numbers 
 is the largest number that divides both of them.
 
 36 = 2 x 2 x 3 x 3
 60 = 2 x 2 x 3 x 5
 
 GCD = Multication of common factors
 	 = 2 x 2 x 3
 	 = 12
 *
 */
public class findHCF {

	@Test
	public void Test(){
		
		int[] input = new int[]{36,60};
		findGCD(input,input.length);
		List<String> s = new ArrayList<>();
		s.size();
	}
	
	public static boolean isNum(String strNum) {
	    boolean ret = true;
	    try {

	        Double.parseDouble(strNum);

	    }catch (NumberFormatException e) {
	        ret = false;
	    }
	    return ret;
	}
	
	// Function to return gcd of a and b
    private int gcd(int a, int b)
    {
        if (a == 0)
            return b;
        System.out.println("b :" + b);
        System.out.println("a :" + a);
        System.out.println("b % a :" + b % a);
        return gcd(b % a, a);
    }
 
    // Function to find gcd of array of
    // numbers
    private int findGCD(int arr[], int n)
    {
        int result = arr[0];
        for (int i = 1; i < n; i++)
            result = gcd(arr[i], result);
 
        return result;
    }
}
