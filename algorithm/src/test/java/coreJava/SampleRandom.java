package coreJava;

import java.util.Random;

import org.testng.annotations.Test;

public class SampleRandom {

	@Test
	public void RandomValue(){
		
		Random rand = new Random();
			
		System.out.println("***** Random integer value WITH BOUNDRY  between 0 and 9 *****");
		int i =0;		
		while(i < 50){
			System.out.print(rand.nextInt(10) + ","); // range in 0 to 9);
			i++;
		}
		System.out.println();
		System.out.println("***** Random integer value *****");
		i =0;		
		while(i < 50){
			System.out.print(rand.nextInt() + ",");
			i++;
		}
		
		System.out.println();
		System.out.println("***** Random boolean value *****");
		i =0;		
		while(i < 50){
			System.out.print(rand.nextBoolean() + ",");
			i++;
		}
		
		System.out.println();
		System.out.println("***** Random double value *****");
		i =0;		
		while(i < 50){
			System.out.print(rand.nextDouble() + ","); 
			i++;
		}
		
		System.out.println();
		System.out.println("***** Random double with nextGaussian *****");
		i =0;		
		while(i < 50){
			System.out.print(rand.nextGaussian() + ","); 
			i++;
		}
		
		System.out.println();
		System.out.println("***** Random floadt value *****");
		i =0;		
		while(i < 50){
			System.out.print(rand.nextFloat() + ","); 
			i++;
		}
		
		System.out.println();
		System.out.println("***** Random long value *****");
		i =0;		
		while(i < 50){
			System.out.print(rand.nextLong() + ","); 
			i++;
		}
		
		System.out.println();
		System.out.println("***** Random Bytes value *****");
		
		i =0;		
		 byte[] nbyte = new byte[50];
		 System.out.println("Before Random " + nbyte);
		while(i < 50){
			rand.nextBytes(nbyte);
			System.out.print( nbyte[0] + ","); 
			i++;
		}
	}
}
