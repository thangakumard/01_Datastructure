package arrayAlgorithms;

import org.testng.annotations.Test;

public class ReplaceWithGreatestElement {

	@Test
	//Time complexity is O(n^2)
	public void approach1(){
		System.out.println("Approach 1");
		int[] a ={16, 17, 4, 3, 5, 2};
		int max = 0;
		
		for(int i=0; i < a.length; i++){
			max = 0;		
			
			for(int j=i+1; j <a.length; j++ ){				
				if(max < a[j]){
					max = a[j];
				}				
			}
			
			if(i == a.length -1) a[i] = -1;
			else a[i] = max;
		}
		
		for(int i=0; i < a.length; i++){
			System.out.println(a[i]);
		}
	}
	
	@Test
	public void approach2(){
		System.out.println("Approach 2");
		int[] a ={16, 17, 4, 3, 5, 2};
		int max = -1;
		int newMax = 0;
		
		for(int i=a.length-1; i >=0 ; i--){
			if(i == 0){
				a[i] = max;
			}
			else if(max < a[i]){
				newMax = a[i];
				a[i] = max;
				max = newMax;
			}
			else
			{
				a[i] = max;
			}
		}
		for(int i=0; i < a.length; i++){
			System.out.println(a[i]);
		}
	}
	
}
