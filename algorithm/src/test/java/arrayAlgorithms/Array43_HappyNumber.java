package arrayAlgorithms;
import java.util.HashSet;

import org.junit.Assert;
import org.testng.annotations.Test;
/******
 * 
 	https://www.geeksforgeeks.org/happy-number/
	Happy Number
	A number is called happy if it leads to 1 after a sequence of steps where in each step number is replaced by sum of squares of its digit that is if we start with Happy Number and keep replacing it with digits square sum, we reach 1.
	Examples :
	
	Input: n = 19
	Output: True
	19 is Happy Number,
	1^2 + 9^2 = 82
	8^2 + 2^2 = 68
	6^2 + 8^2 = 100
	1^2 + 0^2 + 0^2 = 1
	As we reached to 1, 19 is a Happy Number.
	
	Input: n = 20
	Output: False
 *
 */

public class Array43_HappyNumber {
	 HashSet<Integer> myset = new HashSet<Integer>();
	 
	 @Test
	  public void test1(){
		 Assert.assertFalse(bestApproach(234));
		 Assert.assertFalse(isHappyNumber(234));
	 }
	   public boolean isHappyNumber(int n) {        
		        if(n== 1){
		            return true;
		        }
		        else if(!myset.contains(n)){
		            myset.add(n);
		        
		            int i = 0;
		            int m = 0;
		            while(n >0){
		                i = n % 10;                
		                m = m + (i * i);
		                n = n / 10;
		                
		            }
		            return isHappyNumber(m);
		        }
		        else{
		            return false;
		        } 
	   }
	   
	   public boolean bestApproach(int n) {        
	       int slow = n, fast = n;
		        if(n== 1){
		            return true;
		        }
		        else {
	                
	                do{
	                    slow = squareAndAnd(slow);
	                    fast = squareAndAnd(squareAndAnd(fast));
	                    
	                }while(slow != fast);
	                if(slow == 1 || fast == 1){
	                    return true;
	                }
	                else{
	                    return false;
	                }
		        }    
		    
		    }
	    
	    public int squareAndAnd(int n){
	        int i =0, m=0;
	         while(n >0){
		                i = n % 10;                
		                m = m + (i * i);
		                n = n / 10;
		                
		            }
	        return m;
	    }
		    
}
