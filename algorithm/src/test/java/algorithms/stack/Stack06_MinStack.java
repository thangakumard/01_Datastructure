package algorithms.stack;

import java.util.Stack;

/**
 * https://leetcode.com/problems/min-stack/description/
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * Implement the MinStack class:
 *
 * MinStack() initializes the stack object.
 * void push(int val) pushes the element val onto the stack.
 * void pop() removes the element on the top of the stack.
 * int top() gets the top element of the stack.
 * int getMin() retrieves the minimum element in the stack.
 * You must implement a solution with O(1) time complexity for each function.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * Output
 * [null,null,null,null,-3,null,0,-2]
 *
 * Explanation
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top();    // return 0
 * minStack.getMin(); // return -2
 *
 *
 * Constraints:
 *
 * -231 <= val <= 231 - 1
 * Methods pop, top and getMin operations will always be called on non-empty stacks.
 * At most 3 * 104 calls will be made to push, pop, top, and getMin.
 */
public class Stack06_MinStack {
        private Stack<Integer> stack;      // Main stack
        private Stack<Integer> minStack;   // Auxiliary stack tracking minimums

        public Stack06_MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        // Time: O(1) | Space: O(1) amortized
        public void push(int val) {
            stack.push(val);

            // Push to minStack only if it's the new minimum
            // Use <= to handle duplicates correctly during pop
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        // Time: O(1) | Space: O(1)
        public void pop() {
            // If top of main stack equals top of min stack, pop from both
            if (stack.peek().equals(minStack.peek())) {
                minStack.pop();
            }
            stack.pop();
        }

        // Time: O(1) | Space: O(1)
        public int top() {
            return stack.peek();
        }

        // Time: O(1) | Space: O(1)
        public int getMin() {
            return minStack.peek();
        }
}
