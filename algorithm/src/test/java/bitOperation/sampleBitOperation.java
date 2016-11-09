package bitOperation;

import org.testng.Assert;
import org.testng.annotations.Test;



public class sampleBitOperation {
	
	@Test
	public void bitOperation(){
		
		int a = 60;	/* 60 = 0011 1100 */
	      int b = 13;	/* 13 = 0000 1101 */
	      int c = 0;

	      //& (bitwise and)
	      c = a & b;        /* 12 = 0000 1100 */
	      System.out.println("a & b = " + c );
	      
	      //| (bitwise or)
	      c = a | b;        /* 61 = 0011 1101 */
	      System.out.println("a | b = " + c );
	      
	      
	      //^ (bitwise XOR)
	      c = a ^ b;        /* 49 = 0011 0001 */
	      System.out.println("a ^ b = " + c );
	      
	      
	      //~ (bitwise compliment)
	      c = ~a;           /*-61 = 1100 0011 */
	      System.out.println("~a = " + c );
	      
	      
	      //<< (left shift)
	      c = a << 2;       /* 240 = 1111 0000 */
	      System.out.println("a << 2 = " + c );
	      
	      
	      //>> (right shift)	
	      c = a >> 2;       /* 15 = 1111 */
	      System.out.println("a >> 2  = " + c );
	      
	      
	      //>>> (zero fill right shift)
	      c = a >>> 2;      /* 15 = 0000 1111 */
	      System.out.println("a >>> 2 = " + c );
	      
	      Assert.assertTrue(true);
		
	}

}
