package arrayAlgorithms;

import org.testng.annotations.Test;

public class StrictlyIncreasingSubarray {

	
	public void approach1(){
		//linear search
	}
	
	@Test
	public void appraoch2(){
		int[] a={1, 2, 2, 4};
		int len = 1;
		int cnt =0;
		
		for(int i=0; i < a.length-1; i++){
			if(a[i+1] > a[i]){
				len++;
			}
			else{
				cnt += (len * (len-1))/2;
				len = 1;
			}
		}
		if(len > 1){
			cnt += (len * (len-1))/2;
		}
		System.out.println("Number of increasing sub-arrays are : " + cnt);
	}
	
}
