package arrayAlgorithms;

import java.util.HashSet;

import org.testng.annotations.Test;

//Find duplicates in O(n) time and O(1) extra space | Set 1
//http://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/

public class Array20_FindDuplicate_ForPositiveNumbers {

	@Test
	public void approach01(){
		int[] input = {1,2,3,4,5,6,3,5};
		HashSet<Integer> hash = new HashSet<Integer>();
		
		for(int i=0; i < input.length; i++){
			
			if(!hash.add(input[i])){
				System.out.println("Duplicate numbers are : " + input[i]);	
			}
		}
	}
}
