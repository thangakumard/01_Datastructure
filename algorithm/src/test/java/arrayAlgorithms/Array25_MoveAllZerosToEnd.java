package arrayAlgorithms;

import org.testng.annotations.Test;

public class Array25_MoveAllZerosToEnd {

	@Test
	public void approach01(){
		
		System.out.println("Approach 01");
		int[] a = {1,2,0,4,0,5,0,0,6,0,0};
		int count=0;
		
		int left = 0, right = a.length-1;
		
		while(left < right){
			
			if(a[left] == 0){
				
				while(a[right] == 0)
				{
					right--;
				}
				
				
				a[left] = a[right];
				a[right] = 0;
				left ++;
				right--;
				
			}
			else{
				left++;
			}
			count++;
		}
		
		System.out.println("Loop counter :" + count);
		for(int i=0;i < a.length; i++){
			System.out.println(a[i]);			
		}
	}
	
	@Test
	public void approach02(){
		
		System.out.println("Approach 02");

		int[] b = {1,2,0,4,0,50,7,0,2,0};
		int count = 0;
		int size = b.length;
		
		for(int i=0; i < size; i++){
			if(b[i] != 0)
				b[count++] = b[i];
		}
		
		while(count < size)
			b[count++] = 0;
		
		for(int i=0;i < size; i++){
			System.out.println(b[i]);			
		}
	}
}
