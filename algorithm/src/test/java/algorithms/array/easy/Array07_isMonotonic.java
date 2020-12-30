package algorithms.array.easy;

public class Array07_isMonotonic {
	
	public boolean isMonotonic(int[] A) {
		
		boolean isIncreasing = true;
		boolean isDecreasing = true;
		
		for(int i=0; i < A.length; i++) {
			if(A[i] < A[i+1]) {
				isDecreasing = false;
			}
			if(A[i] > A[i+1]) {
				isIncreasing = false;
			}
		}
		return isIncreasing || isDecreasing;
    }

}
