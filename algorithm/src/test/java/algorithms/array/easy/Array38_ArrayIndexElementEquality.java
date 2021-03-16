package algorithms.array.easy;

/******
 * https://www.pramp.com/challenge/jKoA5GAVy9Sr9jGBjz04
 * 
 * Array Index & Element Equality
Given a sorted array arr of distinct integers, write a function indexEqualsValueSearch that returns the lowest index i for which arr[i] == i. Return -1 if there is no such index. Analyze the time and space complexities of your solution and explain its correctness.

Examples:

input: arr = [-8,0,2,5]
output: 2 # since arr[2] == 2

input: arr = [-1,0,3,6]
output: -1 # since no index in arr satisfies arr[i] == i.
Constraints:

[time limit] 5000ms

[input] array.integer arr

1 ≤ arr.length ≤ 100
[output] integer
 *
 */

public class Array38_ArrayIndexElementEquality {

	int indexEqualsValueSearch(int[] arr) {
	    if(arr == null || arr.length == 0)
	      return -1;
	    
	    int minElement = -1, left = 0, right = arr.length -1;
	    while(left <= right){
	      int mid = left + (right -left)/2;
	    
	      if(arr[mid] == mid) { //
	        minElement = mid;
	        right = mid-1; //0,0
	      } 
	      else if(arr[mid] > mid) //4 > 3
	         right = mid -1;//0,0
	      else
	        left = mid + 1;
	      
	    }
	    return minElement;
	  }

}
