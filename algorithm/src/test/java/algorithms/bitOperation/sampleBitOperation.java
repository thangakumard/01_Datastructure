package algorithms.bitOperation;

import org.testng.Assert;
import org.testng.annotations.Test;

/*******************
 * Bitwise Operators
 * 
 * NOT
 * OR
 * AND
 * XOR
 * 
 */

public class sampleBitOperation {
	
	@Test
	public void flipByte(){
		/********************
		 * XOR Truth Table
		 * A	B		A XOR B
		 * 0	0		0
		 * 0	1		1
		 * 1	0		1
		 * 1	1		0
		 */
		//use XOR to flip byte
		int i = 1;
		System.out.println(" To flip didgit : GivenNO XOR 1");
		System.out.print("XOR of 1 is :");
		System.out.println(1^i);
		i = 0;
		System.out.print("XOR of 0 is :");
		System.out.println(1^i);
	}
	
	@Test
	public void complementOperator(){
		byte i = 1;
		System.out.println(" Complement operator :");
		System.out.println(~i);
	}
	
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
	      

	      /******** >> (Signed right shift) 
	       * If the number is negative, then 1 is used as a filler and 
	       if the number is positive, then 0 is used as a filler. ***/
	       
	      a = 100;
	      //>> (right shift)	
	      c = a >> 2;       /* 15 = 1111 */
	      System.out.println("a >> 2  = " + c );      
	      
	      
	      //>> (right shift)	
	      c = -a >> 2;       /* 15 = 1111 */
	      System.out.println("-a >> 2  = " + c );
	      
	      /***********>>> (Unsigned right shift)
	       It always fills 0 irrespective of the sign of the number.*/
	      
	    //>>> (zero fill right shift)
	      c = a >>> 2;      /* 15 = 0000 1111 */
	      System.out.println("a >>> 2 = " + c );
	      
	      //>>> (zero fill right shift)
	      c = -a >>> 2;      /* 15 = 0000 1111 */
	      System.out.println("-a >>> 2 = " + c );
	      
	      Assert.assertTrue(true);
		
	}

}
