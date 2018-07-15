package arrayAlgorithms;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/two-elements-whose-sum-is-closest-to-zero/
public class Array22_SumClosestToZero {

	
	@Test
	//Time complexity n^2
	public void approach01(){

		int[] input = {1, 60, -10, 70, -80, 85};

		int minValue = Math.abs(input[0] + input[1]);
		int a =0, b = 0;

		for(int i =0; i < input.length;i++){

			for(int j=i+1; j < input.length;j++){
				int absValue = Math.abs(input[i] + input[j]);
				if(absValue < minValue){
					minValue = absValue;
					a = input[i];
					b = input[j];
				}
			}
		}

		System.out.println("Approach01 : Values near by 0 are :" + a + " and " + b);
	}


	@Test
	//Time complexity n(log n)
	public void approach02()
	{
		int[] input = {1, 60, -10, 70, -80, 85};
		splitAndSort(input, 0, input.length-1);
	
		int l =0, r = input.length-1, minSum = Integer.MAX_VALUE, total = 0, min_left =0, min_right =0;
		
		while(l < r){
			
			total = input[l] + input[r];
			if(total == 0){
				min_left = l;
				min_right = r;
				break;
			}
			if(minSum > Math.abs(total)){
				minSum = Math.abs(total);
				min_left = l;
				min_right = r;
			}
			
			if(total > 0){
				l++;
			}
			else{
				r--;
			}
		}
	
		System.out.println("Approach02 : Values near by 0 are :" + input[min_left] + " and " + input[min_right]);


	}

	public void splitAndSort(int[] input,int left,int right){

		if(left < right){
			int mid = (left + right)/2;
			splitAndSort(input, left, mid);
			splitAndSort(input, mid+1, right);
			sort(input,left, mid+1,right);
		}
	}

	public void sort(int[] input, int left, int mid, int right){

		int array1Left = left;
		int array1Right = mid-1;
		
		int array2Left = mid;
		int array2Right = right;
		
		int tempIndex = left;
		int[] temp = new int[input.length];
		
		while(array1Left <= array1Right && array2Left <= array2Right){
			if(input[array1Left] < input[array2Left]){
				temp[tempIndex] = input[array1Left];
				array1Left++;
			}
			else{
				temp[tempIndex] = input[array2Left];
				array2Left++;
			}
			
			tempIndex++;
		}
		
		while(array1Left <= array1Right){
			temp[tempIndex] = input[array1Left];
			array1Left++;
			tempIndex++;
		}
		
		while(array2Left <= array2Right){
			temp[tempIndex] = input[array2Left];
			array2Left++;
			tempIndex++;
		}
		
		int totalRecords = right - left + 1;
		
		for (int j = right; totalRecords > 0; totalRecords--) {
			input[j] = temp[j];
			j--;
		}
	}
}
