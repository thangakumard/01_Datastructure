package bitOperation;
import org.testng.annotations.*;
public class Shifting {
	@Test
	public void test(){		
		shiftLeft(10);
		rightLeft(160);
	}
	
	private void shiftLeft(int n){
		System.out.println("BINARY VALUE OF "+ n + " IS :" + Integer.toString(n,2));
		n = n << 1; //multiply by 2 
		System.out.println("AFTER 1 LEFT SHIFT DECIMAL (multiply by 2) : "+ n + " BINARY VALUE IS : " + Integer.toString(n,2));
		n = n << 1; //multiply by 2
		System.out.println("AFTER 1 MORE LEFT SHIFT DECIMAL (multiply by 2) : "+ n + " BINARY VALUE IS : " + Integer.toString(n,2));
		n = n << 2; //multiply by 4
		System.out.println("AFTER 2 LEFT SHIFT DECIMAL (multiply by 4) : "+ n + " BINARY VALUE IS : " + Integer.toString(n,2));
		System.out.println();
	}
	
	private void rightLeft(int n){
		System.out.println("BINARY VALUE OF "+ n + " IS :" + Integer.toString(n,2));
		n = n >> 1; //divided by 2 
		System.out.println("AFTER 1 RIGHT SHIFT DECIMAL (divided by 2) : "+ n + " BINARY VALUE IS : " + Integer.toString(n,2));
		n = n >> 1; //divided by 2
		System.out.println("AFTER 1 MORE RIGHT SHIFT DECIMAL (divided by 2) : "+ n + " BINARY VALUE IS : " + Integer.toString(n,2));
		n = n >> 2; //divided by 4
		System.out.println("AFTER 2 RIGHT SHIFT DECIMAL (divided by 4) : "+ n + " BINARY VALUE IS : " + Integer.toString(n,2));
		System.out.println();
	}
}
