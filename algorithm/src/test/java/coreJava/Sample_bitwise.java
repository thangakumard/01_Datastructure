package coreJava;
import org.testng.annotations.Test;

public class Sample_bitwise {
	
	@Test
	public void testbitwise() {
		int x=7;
		System.out.println("x&27 : " + (x&27));
		System.out.println("x|27 : " + (x|27));
		System.out.println("x^27 : " + (x^27));
		System.out.println("~x : " + (~x));
		
		
		System.out.println("x << 2 : " + (x << 2));
		System.out.println("x >> 2: " + (x >> 2));
		System.out.println("x >>> 2: " + (x >>> 2));
		
		
	    x = 5;
	    System.out.println("Assignment                                       (x = 5) : " + x);

	    x = 5;
	    x += 5;
	    System.out.println("Assign x plus another integer to itself          (x += 5): " + x);

	    x = 5;
	    x -= 4;
	    System.out.println("Assign x minus another integer to itself         (x -= 4): " + x);

	    x = 5;
	    x *= 6;
	    System.out.println("Assign x multiplied by another integer to itself (x *= 6): " + x);

	    x = 5;
	    x /= 5;
	    System.out.println("Assign x divided by another integer to itself    (x /= 5): " + x);

	}

}
