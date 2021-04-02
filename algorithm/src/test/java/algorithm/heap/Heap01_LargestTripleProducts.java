package algorithm.heap;

import java.util.*;

import org.testng.annotations.Test;

public class Heap01_LargestTripleProducts {

	@Test
	private void test() {
		int[] input = new int[] {1, 2, 3, 4, 5};
		int[] result = findMaxProduct(input);
		for(int i : result) {
			System.out.println(i);
		}
	}
	
	int[] findMaxProduct(int[] arr) {
	    // Write your code here
	    int[] result = new int[arr.length];
	    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	    int triple = 1;
	    for(int i = 0; i < arr.length; i++){
	      minHeap.add(i);
	      if(minHeap.size() > 3){
	        minHeap.remove();
	      }
	      
	      if(minHeap.size() == 3){
	        triple = 1;
	        Iterator value = minHeap.iterator();
	        while(value.hasNext()){
	          triple = triple * Integer.parseInt(value.next()+"");
	        }
	        result[i] = triple;
	      }else{
	        result[i] = -1;
	      }
	    }
	    return result;
	  }
}
