package arrayAlgorithms;
import org.testng.annotations.Test;

public class Array48_Sqrt {

	@Test
	public void Test(){
		System.out.println(sqrt(2147483647));
	}
	
	public long sqrt(int x){
		 if (x == 0 || x == 1)
	            return x;
	 
	        // Do Binary Search for floor(sqrt(x))
	        long start = 1, end = x, ans=0;
	        while (start>0 && start <= end)
	        {
	        	System.out.println("Start : " + start + "end : "+ end);
	            long mid = (start + end) / 2;
	 
	            long squre = mid * mid; 
	            // If x is a perfect square
	            if (squre == x)
	                return mid;
	 
	            // Since we need floor, we update answer when mid*mid is
	            // smaller than x, and move closer to sqrt(x)
	            if (squre < x)
	            {
	                start = mid + 1;
	                ans = mid;
	            }
	            else   // If mid*mid is greater than x
	                end = mid - 1;
	        }
	        return ans;
	}
}
