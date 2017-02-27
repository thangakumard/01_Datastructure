package arrayAlgorithms;

import org.testng.annotations.Test;

public class Segregate0sAnd1s {
	
	@Test
	public void approach1(){
		
		int[] a = {0,1,0,1,1,0,0,0,1,1};
		int left =0; 
		int right =a.length-1;
		
		while(left < right){
			while(a[left] == 0 && left < right){
				left++;
			}
			
			while(a[right] == 1 && left < right){
				right--;
			}
			
			if(left < right){
				a[left] = 0;
				a[right] = 1;
				left ++;
				right --;
			}
		}
		for(int i=0; i < a.length; i++){
			System.out.println(a[i]);
		}
	}
	
}
