package algorithm.string;

import org.testng.Assert;
import org.testng.annotations.Test;

/****
 * 
 * https://leetcode.com/problems/judge-route-circle/description/
 * Initially, there is a Robot at position (0, 0). Given a sequence of its moves, 
 * judge if this robot makes a circle, which means it moves back to the original place.

	The move sequence is represented by a string. And each move is repressent by a character. 
	The valid robot moves are R (Right), L (Left), U (Up) and D (down). 
	The output should be true or false representing whether the robot makes a circle.
	
	Example 1:
	Input: "UD"
	Output: true
	Example 2:
	Input: "LL"
	Output: false
 *
 */
public class RobotRouteCircle {

	@Test
	public void TestRobotMovement(){
		
		int x =0, y =0;
		char[] moves = "RRUULLDD".toCharArray();
		
		for(char m : moves){
			if(m == 'L') {
				x--;
			}
			else if(m == 'R'){
				x++;
			}
			else if(m == 'U'){
				y++;
			}
			else if(m == 'D'){
				y--;
			}
		}
		Assert.assertTrue(x == 0 && y ==0);
		
	}
	
	
}
