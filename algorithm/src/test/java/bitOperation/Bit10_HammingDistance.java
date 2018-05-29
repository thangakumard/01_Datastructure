package bitOperation;
import org.testng.annotations.Test;

public class Bit10_HammingDistance {

	
	@Test
	public void Test(){
		hammingDistance(1, 4);
	}
	
	    public int hammingDistance(int x, int y) {       

	        if( x == y)
	            return 0;

	        int hamming = 0;
	        while(x > 0 || y > 0){
	            if((x & 1) != (y & 1)){
	                hamming++;
	            }
                x = x >> 1;
                y = y >> 1;
	        }
	        
	        return hamming;
	        
	    }
}
