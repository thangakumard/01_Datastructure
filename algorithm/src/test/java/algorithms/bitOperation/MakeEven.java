package algorithms.bitOperation;
import org.testng.annotations.*;
public class MakeEven {

	@Test
	public void test() { 
	    short num;  
	    short i;     
	 
	    for(i = 1; i <= 10; i++) { 
	      num = i; 
	      System.out.println("num: " + num); 
	      
	 
	      num = (short) (num & 0xFFFE); // num & 1111 1110 
	 
	      System.out.println("num after turning off bit zero: " 
	                        +  num + "\n");  
	    } 
	  } 
}


