package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RotateArrayToLeft {

	@Test
	public void method1() {
		int[] a={1,2,3,4,5,6,7,8,9};
		int k = 2;
		int[] temp = new int[k];
		for(int i=0; i < k; i++){
			temp[0] = a[i];
		}
		
		int x = 0;
		for(int j=k ; j < a.length; j++,x++){
			a[x] = a[j];
		}
		for(int y =0;y < k ;y++, x++){
			a[x] = temp[y];
		}
		
		for(int z=0; z < a.length;z++){
			System.out.println("Rotated Array");
			System.out.print(a[z] + ",");
		}
		Assert.assertTrue(true);
	}
}
