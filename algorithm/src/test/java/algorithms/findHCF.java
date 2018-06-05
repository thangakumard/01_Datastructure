package algorithms;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class findHCF {

	@Test
	public void Test(){
		
		int[] input = new int[]{2,5};
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
