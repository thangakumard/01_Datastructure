package algorithms.stack;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.LinkedList;

/**
 * https://leetcode.com/problems/asteroid-collision/
 *
 We are given an array asteroids of integers representing asteroids in a row.
 For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left).
 Each asteroid moves at the same speed.
 Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode.
 If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

 Example 1:
 Input: asteroids = [5,10,-5]
 Output: [5,10]
 Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.

 Example 2:
 Input: asteroids = [8,-8]
 Output: []
 Explanation: The 8 and -8 collide exploding each other.

 Example 3:
 Input: asteroids = [10,2,-5]
 Output: [10]
 Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.

 Constraints:
 2 <= asteroids.length <= 104
 -1000 <= asteroids[i] <= 1000
 asteroids[i] != 0
 */

public class Stack09_AsteroidsCollision {
    @Test
    public void arrangeTest(){
        int input[] = new int[]{5,10,-5};
        Assertions.assertThat(asteroidCollision(input)).isEqualTo(new int[]{5,10});

        input = new int[]{-5,-10,5};
        Assertions.assertThat(asteroidCollision(input)).isEqualTo(new int[]{-5,-10,5});

        input = new int[]{2,1,-1,-2,-1,1};
        Assertions.assertThat(asteroidCollision(input)).isEqualTo(new int[]{-1,1});
    }

    /**
     * Time: O(N)
     * Space: O(N)
     */
    public int[] asteroidCollision(int[] asteroids) {

        if(asteroids == null || asteroids.length == 0)
            return asteroids;

        LinkedList<Integer> asteroidStack = new LinkedList<>();
        boolean addToStack = true;


        for(int i=0; i < asteroids.length; i++){
            addToStack = true;
            while(!asteroidStack.isEmpty() &&
                    ((asteroids[i] < 0 && asteroidStack.peek() > 0))) {
                if(Math.abs(asteroids[i]) == Math.abs(asteroidStack.peek()))
                {
                    asteroidStack.pop();
                }else if (asteroidStack.peek() < Math.abs(asteroids[i])){
                    asteroidStack.pop();
                    continue;
                }
                addToStack = false;
                break;
            }
            if(addToStack){
                asteroidStack.push(asteroids[i]);
            }
        }


        int[] result = new int[asteroidStack.size()];
        int i=asteroidStack.size()-1;
        while(!asteroidStack.isEmpty()) {
            result[i] = asteroidStack.pop();
            i--;
        }
        return result;

    }
}
