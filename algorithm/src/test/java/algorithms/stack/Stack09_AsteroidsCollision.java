package algorithms.stack;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.Deque;
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
        Deque<Integer> stack = new ArrayDeque<>();

        for (int a : asteroids) {
            boolean alive = true;

            // Only need to resolve collisions when current moves left (-)
            // and stack top moves right (+)
            while (alive && a < 0 && !stack.isEmpty() && stack.peek() > 0) {
                int top = stack.peek();

                if (top < -a) {
                    // top (smaller) explodes, current keeps moving
                    stack.pop();
                } else if (top == -a) {
                    // equal size: both explode
                    stack.pop();
                    alive = false;
                } else {
                    // top is bigger: current explodes
                    alive = false;
                }
            }

            if (alive) {
                stack.push(a);
            }
        }

        // stack is bottom->top in insertion order; reverse into result array
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
}
