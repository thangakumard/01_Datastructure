package algorithms.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/***
 * https://leetcode.com/problems/implement-queue-using-stacks/
 */
public class Stack13_QueueFromStack {
    public static void main(String[] args) throws IOException {
      ArrayDeque<Integer> inStack = null;
      ArrayDeque<Integer> outStack = null;
  
      public MyQueue() {
          inStack = new ArrayDeque<>();
          outStack = new ArrayDeque<>();
      }
      
      public void push(int x) {
          inStack.push(x);
      }
      
      public int pop() {
          transfer();
          return outStack.pop();
      }
      
      public int peek() {
          transfer();
          return outStack.peek();
      }
      
      public boolean empty() {
          return inStack.isEmpty() && outStack.isEmpty();
      }
  
      public void transfer(){
          if(outStack.isEmpty()){
              while(!inStack.isEmpty()){
                  outStack.push(inStack.pop());
              }
          }
      }
    }
}
