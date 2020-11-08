package algorithms.array;

import org.testng.annotations.Test;

/********
 * 
There are N dominoes in a line, and we place each domino vertically upright.
In the beginning, we simultaneously push some of the dominoes either to the left or to the right.
After each second, each domino that is falling to the left pushes the adjacent domino on the left.
Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.
When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.
For the purposes of this question, we will consider that a falling domino expends no additional force to a falling or already fallen domino.
Given a string "S" representing the initial state. S[i] = 'L', 
if the i-th domino has been pushed to the left; S[i] = 'R', 
if the i-th domino has been pushed to the right; S[i] = '.', if the i-th domino has not been pushed.
Return a string representing the final state. 
 *
 */
public class Array45_PushDominoes {

	
	
	@Test
	public void pushDominoes(){
		
	}
	
	
	private String pushDomminoesAlgo(String input){
		String result="";
		
		char[] dominoes = input.toCharArray();
		
		char[] resultArray = new char[dominoes.length];
		int leftPointer = 0;
		int rightPointer = dominoes.length-1;
		for(leftPointer=0; leftPointer < dominoes.length/2; leftPointer++){
			if(leftPointer < rightPointer){
				
			}
		}
		return result;
	}
}
