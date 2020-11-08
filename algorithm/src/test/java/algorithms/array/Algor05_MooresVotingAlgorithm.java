package algorithms.array;

import org.testng.annotations.*;

/**************
 * 
 * Majority Element: A majority element in an array A[] of size n is an element 
 * that appears more than n/2 times (and hence there is at most one such element).

	Write a function which takes an array and emits the majority element (if it exists), 
	otherwise prints NONE as follows:

       I/P : 3 3 4 2 4 4 2 4 4
       O/P : 4 

       I/P : 3 3 4 2 4 4 2 4
       O/P : NONE
 *
 */


public class Algor05_MooresVotingAlgorithm {

	@Test
	public void test(){
		int[] input = {3, 3, 4, 2, 4, 4, 2, 4, 4}; // 4
		// int[] input = {3, 3, 4, 2, 4, 4, 2, 4}; //NONE
		MooresVoting(input);
	}
	
	public void MooresVoting(int[] input)
	{
		int candidate = findCandidate(input);
		if(isMajority(input, candidate)){
			System.out.println("MAJORITY_ELEMENT :" + candidate);
		}else{
			System.out.println("*********** NONE ********");
		}
	}
	
	private int findCandidate(int[] input){		
		int candidate = 0;
		int count = 1;
		int i=1;
		while(i < input.length){
			if(input[candidate] == input[i]){
				count ++;
			}
			else{
				count--;
			}
			if(count == 0){
				candidate = i;
				count = 1;
			}
			i++;
		}
		return input[candidate];
	}
	
	private boolean isMajority(int[] input, int candidate){
		int count = 0;
		for(int i=0; i< input.length; i++){			
			if(input[candidate] == input[i]){
				count++;
			}			
			if(count > input.length/2)
				break;
		}
		if(count > input.length/2){
			return true;
		}
		else{
			return false;
		}
	}
}
