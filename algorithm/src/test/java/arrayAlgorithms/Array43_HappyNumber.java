package arrayAlgorithms;
import java.util.HashSet;

import org.junit.Assert;
import org.testng.annotations.Test;

public class Array43_HappyNumber {
	 HashSet<Integer> myset = new HashSet<Integer>();
	 
	 @Test
	  public void test1(){
		 Assert.assertFalse(bestApproach(234));
		 Assert.assertFalse(Approach1(234));
	 }
	   public boolean Approach1(int n) {        
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
		            return Approach1(m);
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
	                if(slow == 1){
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
